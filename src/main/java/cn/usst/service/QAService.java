package cn.usst.service;

import cn.usst.pojo.dto.*;
import com.github.pagehelper.PageInfo;

public interface QAService {
    // 发布问题
    void postQuestion(QuestionPostDTO dto, Long studentId);

    // 搜索问题
    PageInfo<QuestionDetailDTO> searchQuestions(QuestionSearchDTO dto);

    // 获取详情
    QuestionDetailDTO getQuestionDetail(Long id);

    // 管理员删除问题
    void deleteQuestion(Long id);

    // 管理员删除回答
    void deleteAnswer(Long id);


    AnswerDTO getAnswerById(Long id);

    // 管理员修改问题 (改用 DTO)
    void updateQuestion(QuestionUpdateDTO dto);

    // 管理员修改回答 (改用 DTO)
    void updateAnswer(AnswerUpdateDTO dto);

    PageInfo<QuestionDetailDTO> getStudentOwnQuestions(QuestionSearchDTO dto, Long studentId);

    void deleteQuestionByStudent(Long id, Long studentId);

    void updateQuestionByStudent(QuestionUpdateDTO dto, Long studentId);
}