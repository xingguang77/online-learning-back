package cn.usst.service;

import cn.usst.pojo.LoginInfo;
import cn.usst.pojo.Result;
import cn.usst.pojo.User;
import cn.usst.pojo.dto.LoginDTO;
import cn.usst.pojo.dto.PasswordDTO;
import cn.usst.pojo.dto.RegisterDTO;
import cn.usst.pojo.dto.UserUpdateDTO;

public interface UserService {

    //登录功能
    LoginInfo login(LoginDTO dto);

    //查询回显
    User selectById(Long id);

    //修改邮箱头像
    void updateProfile(UserUpdateDTO dto);

    //修改密码
    void changePassword(PasswordDTO dto);

    // 学生注册
    void register(RegisterDTO dto);
}

