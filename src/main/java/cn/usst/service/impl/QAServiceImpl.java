package cn.usst.service.impl;

import cn.usst.mapper.QAMapper;
import cn.usst.pojo.Question;
import cn.usst.pojo.dto.*;
import cn.usst.service.QAService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QAServiceImpl implements QAService {

    @Autowired
    private QAMapper qaMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postQuestion(QuestionPostDTO dto, Long studentId) {
        // 1. 插入问题
        Question q = new Question();
        q.setTitle(dto.getTitle());
        q.setContent(dto.getContent());
        q.setCourseId(dto.getCourseId());
        q.setStudentId(studentId);
        // classId 在 mapper sql 中通过子查询自动填入

        qaMapper.insertQuestion(q);

        // 2. 插入附件
        if (dto.getFileUrls() != null && !dto.getFileUrls().isEmpty()) {
            qaMapper.insertQuestionFiles(q.getId(), dto.getFileUrls());
        }
    }

    @Override
    public PageInfo<QuestionDetailDTO> searchQuestions(QuestionSearchDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<QuestionDetailDTO> list = qaMapper.selectQuestionList(dto);
        return new PageInfo<>(list);
    }

    @Override
    public QuestionDetailDTO getQuestionDetail(Long id) {
        // 1. 查问题基本信息
        QuestionDetailDTO detail = qaMapper.selectQuestionBaseInfo(id);
        if (detail != null) {
            // 2. 查回答列表
            List<AnswerDTO> answers = qaMapper.selectAnswersByQuestionId(id);
            detail.setAnswers(answers);
        }
        return detail;
    }

    /**
     * 删除问题 (物理删除)
     * 逻辑：先删关联数据（回答附件 -> 回答 -> 问题附件），最后删问题本体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQuestion(Long id) {
        // 1. 删除该问题下所有回答的附件
        qaMapper.deleteAnswerFilesByQuestionId(id);

        // 2. 删除该问题下的所有回答
        qaMapper.deleteAnswersByQuestionId(id);

        // 3. 删除该问题的附件
        qaMapper.deleteQuestionFilesByQuestionId(id);

        // 4. 最后删除问题本体
        qaMapper.deleteQuestionById(id);
    }

    /**
     * 删除单条回答 (物理删除)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAnswer(Long id) {
        // 1. 删除该回答的附件
        qaMapper.deleteAnswerFilesByAnswerId(id);

        // 2. 删除回答本体
        qaMapper.deleteAnswerById(id);
    }

    @Override
    public AnswerDTO getAnswerById(Long id) {
        return qaMapper.selectAnswerById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuestion(QuestionUpdateDTO dto) {
        // 1. 更新问题文本
        qaMapper.updateQuestionContent(dto.getId(), dto.getTitle(), dto.getContent());

        // 2. 更新附件 (策略：先删后加)
        // 2.1 删除旧附件
        qaMapper.deleteQuestionFilesByQuestionId(dto.getId());

        // 2.2 插入新附件
        if (dto.getFileUrls() != null && !dto.getFileUrls().isEmpty()) {
            qaMapper.insertQuestionFiles(dto.getId(), dto.getFileUrls());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAnswer(AnswerUpdateDTO dto) {
        // 1. 更新回答文本
        qaMapper.updateAnswerContent(dto.getId(), dto.getContent());

        // 2. 更新附件 (策略：先删后加)
        // 2.1 删除旧附件
        qaMapper.deleteAnswerFilesByAnswerId(dto.getId());

        // 2.2 插入新附件
        if (dto.getFileUrls() != null && !dto.getFileUrls().isEmpty()) {
            qaMapper.insertAnswerFiles(dto.getId(), dto.getFileUrls());
        }
    }

    @Override
    public PageInfo<QuestionDetailDTO> getStudentOwnQuestions(QuestionSearchDTO dto, Long studentId) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        // Mapper 新增 selectMyQuestions
        List<QuestionDetailDTO> list = qaMapper.selectMyQuestions(dto, studentId);
        return new PageInfo<>(list);
    }

    @Override
    public void deleteQuestionByStudent(Long id, Long studentId) {
        // 1. 鉴权
        Long askerId = qaMapper.selectAskerId(id);
        if (askerId == null || !askerId.equals(studentId)) {
            throw new RuntimeException("无权删除此问题");
        }
        // 2. 复用之前的物理删除逻辑
        this.deleteQuestion(id);
    }

    @Override
    public void updateQuestionByStudent(QuestionUpdateDTO dto, Long studentId) {
        // 1. 鉴权
        Long askerId = qaMapper.selectAskerId(dto.getId());
        if (askerId == null || !askerId.equals(studentId)) {
            throw new RuntimeException("无权修改此问题");
        }
        // 2. 复用之前的更新逻辑
        this.updateQuestion(dto);
    }
}