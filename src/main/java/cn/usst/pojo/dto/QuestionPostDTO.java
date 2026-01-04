package cn.usst.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionPostDTO {
    private String title;
    private String content;
    private Long courseId;      // 提问所属课程
    private List<String> fileUrls; // 问题附件
}