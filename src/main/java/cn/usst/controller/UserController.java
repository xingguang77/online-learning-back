package cn.usst.controller;

import cn.usst.pojo.Result;
import cn.usst.pojo.User;
import cn.usst.pojo.dto.PasswordDTO;
import cn.usst.pojo.dto.UserUpdateDTO;
import cn.usst.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据id当前登录用户信息
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Long id) {
        User user = userService.selectById(id);
        user.setPassword(null);
        return Result.success(user);
    }


    /**
     * 修改个人资料
     */
    @PutMapping("/profile")
    public Result updateProfile(@RequestBody UserUpdateDTO dto) {
        log.info("修改个人资料：{}", dto);
        userService.updateProfile(dto);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result changePassword(@RequestBody PasswordDTO dto) {
        log.info("修改密码");
        userService.changePassword(dto);
        return Result.success();
    }
}

