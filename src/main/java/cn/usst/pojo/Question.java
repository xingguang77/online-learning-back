package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private Long id;

    /** 问题标题 */
    private String title;

    /** 问题内容 */
    private String content;

    /** 所属课程 */
    private Long courseId;

    /** 班级（决定教师可见） */
    private Long classId;

    /** 提问学生 user.id */
    private Long studentId;

    /** unanswered / answered */
    private Integer status;

    private LocalDateTime createTime;
}

