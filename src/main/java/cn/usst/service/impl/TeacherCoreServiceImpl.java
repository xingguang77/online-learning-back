package cn.usst.service.impl;

import cn.usst.mapper.QAMapper;
import cn.usst.mapper.TeacherCoreMapper;
import cn.usst.pojo.Answer;
import cn.usst.pojo.Question;
import cn.usst.pojo.Resource;
import cn.usst.pojo.dto.*;
import cn.usst.service.NotificationService;
import cn.usst.service.TeacherCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherCoreServiceImpl implements TeacherCoreService {

    @Autowired
    private TeacherCoreMapper teacherCoreMapper;

    // 1. 注入通知服务
    @Autowired
    private NotificationService notificationService;

    // 2. 注入 QA Mapper 用于查询提问者ID
    @Autowired
    private QAMapper qaMapper;
    // TODO: 注入 NotificationService (成员E负责)
    // @Autowired
    // private NotificationService notificationService;

    @Override
    public List<Map<String, Object>> getMyClasses(Long userId) {
        Long teacherId = teacherCoreMapper.getTeacherIdByUserId(userId);
        if (teacherId == null) {
            throw new RuntimeException("当前用户不是教师");
        }

        List<ClazzVO> classes = teacherCoreMapper.selectClassesByTeacherId(teacherId);

        // 封装返回结果，增加"未回答问题数"
        List<Map<String, Object>> result = new ArrayList<>();
        for (ClazzVO clazz : classes) {
            Map<String, Object> map = new HashMap<>();
            map.put("classInfo", clazz);
            // 需求 4-a: 新问题提醒
            int unansweredCount = teacherCoreMapper.countUnansweredQuestions(clazz.getId());
            map.put("unansweredCount", unansweredCount);
            result.add(map);
        }
        return result;
    }

    @Override
    public List<QuestionDTO> getClassQuestions(Long classId, Integer status) {
        return teacherCoreMapper.selectQuestionsByClassId(classId, status);
    }

    @Override
    @Transactional
    public void publishResource(ResourcePostDTO dto, Long userId) {
        // 1. 保存资源主体
        Resource resource = new Resource();
        resource.setTitle(dto.getTitle());
        resource.setDescription(dto.getDescription());
        resource.setCourseId(dto.getCourseId());
        resource.setClassId(dto.getClassId());
        resource.setUserId(userId);
        resource.setUploaderRole("teacher");
        // 需求 4-d: 权限设置
        resource.setVisibility(dto.getVisibility()); // "all" or "class_only"

        teacherCoreMapper.insertResource(resource);

        // 2. 保存附件
        if (dto.getFileUrls() != null && !dto.getFileUrls().isEmpty()) {
            teacherCoreMapper.insertResourceFiles(resource.getId(), dto.getFileUrls());
        }
    }




    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyQuestion(AnswerPostDTO dto, Long userId) {
        Long teacherId = teacherCoreMapper.getTeacherIdByUserId(userId);
        if (teacherId == null) {
            throw new RuntimeException("当前用户不是教师");
        }
        // --- 原有的保存回答逻辑 ---
        Answer answer = new Answer();
        answer.setQuestionId(dto.getQuestionId());
        answer.setContent(dto.getContent());
        answer.setTeacherId(teacherId);
        answer.setIsDeleted(0);
        answer.setCreateTime(LocalDateTime.now());

        teacherCoreMapper.insertAnswer(answer);

        // 处理附件
        if (dto.getFileUrls() != null && !dto.getFileUrls().isEmpty()) {
            teacherCoreMapper.insertAnswerFiles(answer.getId(), dto.getFileUrls());
        }

        // 更新问题状态为已解决
        teacherCoreMapper.updateQuestionStatus(dto.getQuestionId(), 1);

        // --- 【新增】核心逻辑：发送通知 ---
        // 1. 查出这个问题是谁提的 (复用之前的 selectQuestionDetailById 或类似方法)
        QuestionDetailDTO question = qaMapper.selectQuestionBaseInfo(dto.getQuestionId());

        if (question != null) {
            // 2. 构造通知内容
            String msg = "您的问题 [" + question.getTitle() + "] 收到了新的回答，快去看看吧！";

            // 3. 调用 sendNotification，将消息写入数据库
            // question.getStudentId() 是接收者，msg 是内容
            notificationService.sendNotification(question.getStudentId(), msg,question.getId());
        }
    }

    @Override
    public List<QuestionDTO> getAllQuestionsByTeacherId(Long userId) {
        Long teacherId = teacherCoreMapper.getTeacherIdByUserId(userId);
        return teacherCoreMapper.selectAllQuestionsByTeacherId(teacherId);
    }

    @Override
    public void updateResourceVisibility(Long resourceId, Integer visibility) {
        // 更新 resource 表的 visibility 字段
        teacherCoreMapper.updateResourceVisibility(resourceId, visibility);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAnswer(Long answerId, Long userId) {
        // 1. 校验权限
        Long ownerId = teacherCoreMapper.getAnswerOwnerId(answerId);
        Long teacherId = teacherCoreMapper.getTeacherIdByUserId(userId);

        // 注意判空，防止脏数据导致 NullPointerException
        if (ownerId == null || !ownerId.equals(teacherId)) {
            throw new RuntimeException("无权删除他人的回答");
        }

        // 2. 逻辑删除回答主体 (设置 is_deleted = 1)
        teacherCoreMapper.deleteAnswerById(answerId);

        // 3. 物理删除关联的附件 (清理 answer_file 表)
        teacherCoreMapper.deleteAnswerFiles(answerId);

        // 4. (可选) 检查问题状态回滚逻辑...
        // 如果需要实现“若无其他回答则将问题置为未回答”，可以在此处统计剩余回答数并更新 question 表
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAnswer(AnswerUpdateDTO dto, Long userId) {
        // 1. 校验权限
        Long ownerId = teacherCoreMapper.getAnswerOwnerId(dto.getId());
        Long teacherId = teacherCoreMapper.getTeacherIdByUserId(userId);

        if (ownerId == null || !ownerId.equals(teacherId)) {
            throw new RuntimeException("无权修改他人的回答");
        }

        // 2. 更新回答文本内容
        teacherCoreMapper.updateAnswerContent(dto);

        // 3. 更新附件 (策略：全量覆盖 -> 先删除旧的，再插入新的)
        // 3.1 删除该回答下的所有旧附件
        teacherCoreMapper.deleteAnswerFiles(dto.getId());

        // 3.2 如果有新附件，则批量插入
        if (dto.getFileUrls() != null && !dto.getFileUrls().isEmpty()) {
            teacherCoreMapper.insertAnswerFiles(dto.getId(), dto.getFileUrls());
        }
    }

    @Override
    public AnswerDTO getAnswerByQuestionId(Long questionId) {
        return teacherCoreMapper.selectAnswerByQuestionId(questionId);
    }
}