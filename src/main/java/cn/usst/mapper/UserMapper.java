package cn.usst.mapper;

import cn.usst.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findByUsername(String username);

    User findById(Long id);

    void updateProfile(User user);

    void updatePassword(User user);

    // 新增用户（需要返回自增ID）
    void insert(User user);

    // 根据ID删除用户
    void deleteById(Long id);

    // 仅修改姓名（用于管理员修改教师姓名）
    void updateName(User user);
}

