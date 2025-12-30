package cn.usst.pojo.dto;

import cn.usst.pojo.Clazz;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClazzVO extends Clazz {
    // 额外展示的字段
    private String courseName;   // 课程名称
    private String teacherName;  // 教师姓名 (对应 user.name)
}