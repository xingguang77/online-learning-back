package cn.usst.pojo.dto;

import cn.usst.pojo.Question;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true) // 继承父类的 equals/hashCode
public class QuestionDTO extends Question {
    // 增加附件 URL 列表字段
    private List<String> fileUrls;
    // === 新增字段 (解决回答不显示的问题) ===
    private Long answerId;          // 回答ID (用于修改/删除)
    private String answerContent;   // 回答内容 (用于回显)
    private String studentName;     // 提问学生姓名 (用于列表展示)
    private String courseName;
    // 新增：专门存放【老师回答】的附件
    private List<String> answerFileUrls;
}