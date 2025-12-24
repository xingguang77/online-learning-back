package cn.usst.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClazzQueryDTO extends QueryDTO {
    // 继承了 page, pageSize
    private String courseName;  // 按课程名搜索
    private String teacherName; // 按教师名搜索
}