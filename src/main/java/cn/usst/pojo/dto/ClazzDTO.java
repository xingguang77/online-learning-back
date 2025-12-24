package cn.usst.pojo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzDTO {
    private Long id;
    private Long courseId;   // 关联的课程ID
    private String className;// 班级名称
    private Long teacherId;  // 关联的教师ID
}