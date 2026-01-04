package cn.usst.service.impl;

import cn.usst.exception.LoginFailedException;
import cn.usst.mapper.UserMapper;
import cn.usst.pojo.LoginInfo;
import cn.usst.pojo.User;
import cn.usst.pojo.dto.LoginDTO;
import cn.usst.pojo.dto.PasswordDTO;
import cn.usst.pojo.dto.RegisterDTO;
import cn.usst.pojo.dto.UserUpdateDTO;
import cn.usst.service.UserService;
import cn.usst.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 注入时确保有 BeanUtils
    @Override
    public void register(RegisterDTO dto) {
        // 1. 校验用户名是否已存在
        User existUser = userMapper.findByUsername(dto.getUsername());
        if (existUser != null) {
            throw new RuntimeException("用户名已存在，请更换");
        }

        // 2. 封装用户对象
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setUserType(3); // 核心：强制设置类型为 3 (学生)
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // 3. 插入数据库
        userMapper.insert(user);
    }
    @Override
    public LoginInfo login(LoginDTO dto) {
        User user = userMapper.findByUsername(dto.getUsername());
        if (user == null || !Objects.equals(dto.getPassword(), user.getPassword())) {
            throw new LoginFailedException("用户名或密码错误~");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = JwtUtils.generateToken(claims);
        return new LoginInfo(user.getId(),user.getUsername(),  user.getName(),user.getUserType(), token);
    }

    /**
     * 获取当前登录用户
     */
    @Override
    public User selectById(Long id) {
        User user = userMapper.findById(id);
        if (user != null) {
            user.setPassword(null); // 不返回密码
        }
        return user;
    }

    /**
     * 修改个人资料
     */
    @Override
    public void updateProfile(UserUpdateDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setImage(dto.getImage());
        user.setEmail(dto.getEmail());
        userMapper.updateProfile(user);
    }

    /**
     * 修改密码
     */
    @Override
    public void changePassword(PasswordDTO dto) {
        User user = userMapper.findById(dto.getId());
        if (!Objects.equals(dto.getOldPassword(), user.getPassword())) {
            throw new LoginFailedException("原密码错误");
        }
        user.setPassword(dto.getNewPassword());
        userMapper.updatePassword(user);
    }
}

