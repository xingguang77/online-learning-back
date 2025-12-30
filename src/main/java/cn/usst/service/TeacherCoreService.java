package cn.usst.service;

import cn.usst.pojo.dto.*;

import java.util.List;
import java.util.Map;

public interface TeacherCoreService {
    // 获取教师的班级列表（包含未回答问题数）
    List<Map<String, Object>> getMyClasses(Long userId);

    // 获取某班级的问题列表
    List<QuestionDTO> getClassQuestions(Long classId, Integer status);

    // 发布资源
    void publishResource(ResourcePostDTO dto, Long userId);

    // 回答问题
    void replyQuestion(AnswerPostDTO dto, Long userId);

    List<QuestionDTO> getAllQuestionsByTeacherId(Long teacherId); // 新增

    void updateResourceVisibility(Long resourceId, Integer visibility); // 新增

    void deleteAnswer(Long answerId, Long userId); // 新增
    void updateAnswer(AnswerUpdateDTO dto, Long userId); // 新增

    AnswerDTO getAnswerByQuestionId(Long questionId);
}