package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    private Long id;

    private Long questionId;

    /** 教师 teacher.id */
    private Long teacherId;

    private String content;

    /** 0正常 1删除 */
    private Integer isDeleted;

    private LocalDateTime createTime;
}

