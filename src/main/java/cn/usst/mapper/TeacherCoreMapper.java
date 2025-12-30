package cn.usst.mapper;

import cn.usst.pojo.*;
import cn.usst.pojo.dto.AnswerDTO;
import cn.usst.pojo.dto.AnswerUpdateDTO;
import cn.usst.pojo.dto.ClazzVO;
import cn.usst.pojo.dto.QuestionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherCoreMapper {

    // 根据 userId 查询 teacherId
    @Select("SELECT id FROM teacher WHERE user_id = #{userId}")
    Long getTeacherIdByUserId(Long userId);

    // 查询该教师所带的所有班级（包含课程名称）
    List<ClazzVO> selectClassesByTeacherId(Long teacherId);

    // 查询某班级下的所有问题（带学生姓名）
    List<QuestionDTO> selectQuestionsByClassId(@Param("classId") Long classId, @Param("status") Integer status);

    // 统计某班级未回答问题的数量
    @Select("SELECT COUNT(*) FROM question WHERE class_id = #{classId} AND status = 0")
    int countUnansweredQuestions(Long classId);

    // 插入资源
    void insertResource(Resource resource);

    // 批量插入资源附件
    void insertResourceFiles(@Param("resourceId") Long resourceId, @Param("urls") List<String> urls);

    // 插入回答
    void insertAnswer(Answer answer);

    // 批量插入回答附件
    void insertAnswerFiles(@Param("answerId") Long answerId, @Param("urls") List<String> urls);

    // 更新问题状态
    @Select("UPDATE question SET status = #{status} WHERE id = #{id}")
    void updateQuestionStatus(@Param("id") Long id, @Param("status") Integer status);

    // 查询问题详情用于通知
    @Select("SELECT * FROM question WHERE id = #{id}")
    Question selectQuestionById(Long id);

    // 查询某教师名下的所有问题
    List<QuestionDTO> selectAllQuestionsByTeacherId(Long teacherId);

    // 更新资源可见性
    void updateResourceVisibility(@Param("resourceId") Long resourceId, @Param("visibility") Integer visibility);

    // 获取回答归属的 teacher_id
    Long getAnswerOwnerId(Long answerId);

    // 逻辑删除回答
    void deleteAnswerById(Long answerId);

    // 更新回答内容
    void updateAnswerContent(AnswerUpdateDTO dto);

    // 删除回答的所有附件
    void deleteAnswerFiles(Long answerId);

    // ... 其他方法
    // 新增：根据问题ID查询回答详情（含附件）
    AnswerDTO selectAnswerByQuestionId(Long questionId);
}