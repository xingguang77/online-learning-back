package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerFile {

    private Long id;

    private Long answerId;

    private String fileUrl;
}

