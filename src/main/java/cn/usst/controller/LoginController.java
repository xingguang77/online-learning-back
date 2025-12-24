package cn.usst.controller;

import cn.usst.pojo.User;
import cn.usst.pojo.LoginInfo;
import cn.usst.pojo.Result;
import cn.usst.pojo.dto.LoginDTO;
import cn.usst.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Result login(@RequestBody LoginDTO dto) {
        log.info("员工来登录啦 , {}", dto);
        LoginInfo loginInfo = userService.login(dto);
        return Result.success(loginInfo);
    }

}
