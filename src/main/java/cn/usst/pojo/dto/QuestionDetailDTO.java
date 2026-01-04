package cn.usst.pojo.dto;

import cn.usst.pojo.Question;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionDetailDTO extends Question {
    private String studentName; // 提问者姓名
    private String courseName;  // 课程名称
    private List<String> fileUrls; // 问题附件

    // 该问题下的所有回答
    private List<AnswerDTO> answers;
}