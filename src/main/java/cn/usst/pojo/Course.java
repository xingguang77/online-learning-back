package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private Long id;

    /** 课程名称 */
    private String name;

    /** 课程描述 */
    private String description;

    /** 开课学院 */
    private String college;

    /** 1启用 0停用 */
    private Integer status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

