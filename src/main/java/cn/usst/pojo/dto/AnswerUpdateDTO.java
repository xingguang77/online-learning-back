package cn.usst.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class AnswerUpdateDTO {
    private Long id;          // 回答的ID (Answer表的主键)
    private String content;   // 修改后的文本内容
    private List<String> fileUrls; // 修改后的附件列表 (全量覆盖)
}