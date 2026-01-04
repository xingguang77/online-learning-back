package cn.usst.controller;

import cn.usst.pojo.User;
import cn.usst.pojo.LoginInfo;
import cn.usst.pojo.Result;
import cn.usst.pojo.dto.LoginDTO;
import cn.usst.pojo.dto.RegisterDTO;
import cn.usst.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO dto) {
        log.info("员工来登录啦 , {}", dto);
        LoginInfo loginInfo = userService.login(dto);
        return Result.success(loginInfo);
    }

    /**
     * 学生注册接口
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO dto) {
        // 简单的参数校验
        if(dto.getUsername() == null || dto.getPassword() == null || dto.getName() == null) {
            return Result.error("请补全注册信息");
        }
        userService.register(dto);
        return Result.success("注册成功，请登录");
    }

}
