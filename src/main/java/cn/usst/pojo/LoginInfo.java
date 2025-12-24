package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功结果封装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private Long id; //员工ID
    private String username; //用户名
    private String name; //姓名
    private Integer userType; // 角色：1=管理员，2=教师，3=学生
    private String token; //令牌
}

