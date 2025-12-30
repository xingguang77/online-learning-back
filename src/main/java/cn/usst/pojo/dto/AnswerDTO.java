package cn.usst.pojo.dto;

import cn.usst.pojo.Answer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerDTO extends Answer {
    // 专门存放回答的附件
    private List<String> fileUrls;
}