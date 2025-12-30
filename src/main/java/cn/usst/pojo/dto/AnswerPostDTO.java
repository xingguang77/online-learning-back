package cn.usst.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class AnswerPostDTO {
    private Long questionId;
    private String content;
    // 附件URL列表
    private List<String> fileUrls;
}