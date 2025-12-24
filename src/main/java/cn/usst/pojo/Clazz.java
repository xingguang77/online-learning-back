package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clazz {

    private Long id;

    /** 所属课程 */
    private Long courseId;

    /** 班级名称 */
    private String className;

    /** 主讲教师 teacher.id */
    private Long teacherId;

    /** 1启用 0停用 */
    private Integer status;

    private LocalDateTime createTime;
}

