package cn.usst.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionUpdateDTO {
    private Long id;
    private String title;
    private String content;
    private List<String> fileUrls; // 新的附件列表
}