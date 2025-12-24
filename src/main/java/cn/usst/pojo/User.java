package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id; // 用户ID（主键）
    private String username; // 用户名
    private String password; // 密码（加密后）
    private String name; // 密码（加密后）
    private Integer userType; // 角色：1=管理员，2=教师，3=学生
    private String image; // 头像路径
    private String email; // 邮箱
    private LocalDateTime  createTime; // 创建时间
    private LocalDateTime updateTime; // 创建时间
}
