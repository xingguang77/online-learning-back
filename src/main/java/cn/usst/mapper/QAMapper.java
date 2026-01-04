package cn.usst.mapper;

import cn.usst.pojo.Question;
import cn.usst.pojo.dto.AnswerDTO;
import cn.usst.pojo.dto.QuestionDTO;
import cn.usst.pojo.dto.QuestionDetailDTO;
import cn.usst.pojo.dto.QuestionSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QAMapper {

    // ... 其他原有方法保持不变 ...

    // 1. 插入问题主体
    void insertQuestion(Question question);

    // 2. 插入问题附件
    void insertQuestionFiles(@Param("questionId") Long questionId, @Param("urls") List<String> urls);

    // 3. 搜索问题列表
    List<QuestionDetailDTO> selectQuestionList(@Param("dto") QuestionSearchDTO dto);

    // 4. 获取问题详情
    QuestionDetailDTO selectQuestionBaseInfo(Long id);

    // 5. 获取某问题的所有回答
    List<AnswerDTO> selectAnswersByQuestionId(Long questionId);

    // 查询单个回答
    AnswerDTO selectAnswerById(Long id);

    // [新增] 插入回答附件 (用于更新回答时重新写入附件)
    void insertAnswerFiles(@Param("answerId") Long answerId, @Param("urls") List<String> urls);
    // === 修改点：删除相关方法（改为物理删除） ===

    // 删除问题本体
    void deleteQuestionById(Long id);

    // 删除问题的附件
    void deleteQuestionFilesByQuestionId(Long questionId);

    // 删除问题下的所有回答
    void deleteAnswersByQuestionId(Long questionId);

    // 删除问题下所有回答的附件 (级联清理)
    void deleteAnswerFilesByQuestionId(Long questionId);

    // === 其他修改相关方法 ===

    // 修改问题内容
    @Update("UPDATE question SET title = #{title}, content = #{content} WHERE id = #{id}")
    void updateQuestionContent(@Param("id") Long id, @Param("title") String title, @Param("content") String content);

    // 修改回答内容
    @Update("UPDATE answer SET content = #{content} WHERE id = #{id}")
    void updateAnswerContent(@Param("id") Long id, @Param("content") String content);

    // 删除回答 (单条) - 也改为物理删除比较好，保持一致
    // 原逻辑删除: @Update("UPDATE answer SET is_deleted = 1 WHERE id = #{id}")
    // 现改为物理删除:
    void deleteAnswerById(Long id);

    // 删除单条回答对应的附件
    void deleteAnswerFilesByAnswerId(Long answerId);

    // Mapper 接口新增
    List<QuestionDetailDTO> selectMyQuestions(@Param("dto") QuestionSearchDTO dto, @Param("studentId") Long studentId);

    Long selectAskerId(Long id);
}