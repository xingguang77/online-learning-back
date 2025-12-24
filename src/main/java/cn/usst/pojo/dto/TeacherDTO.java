package cn.usst.pojo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private Long id; // teacher表的主键
    private Long userId; // user表的主键
    private String username;
    private String password; // 新增时必填
    private String name;
    private String title;
    private String introduction;
}