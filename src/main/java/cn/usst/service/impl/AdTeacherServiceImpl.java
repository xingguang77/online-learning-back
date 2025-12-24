package cn.usst.service.impl;

import cn.usst.mapper.AdTeacherMapper;
import cn.usst.mapper.UserMapper;
import cn.usst.pojo.PageResult;
import cn.usst.pojo.Teacher;
import cn.usst.pojo.User;
import cn.usst.pojo.UserType;
import cn.usst.pojo.dto.QueryDTO;
import cn.usst.pojo.dto.TeacherDTO;
import cn.usst.service.AdTeacherService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdTeacherServiceImpl implements AdTeacherService {

    @Autowired
    private AdTeacherMapper teacherMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public PageResult<TeacherDTO> page(QueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        // 复用 CourseQueryDTO 中的 name 字段作为搜索条件
        List<TeacherDTO> list = teacherMapper.list(queryDTO.getName());
        Page<TeacherDTO> p = (Page<TeacherDTO>) list;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 开启事务
    public void add(TeacherDTO dto) {
        // === 方案 B：主动校验账号是否已存在 ===
        // 假设你的 userMapper 中有名为 getByUsername 的方法
        User existingUser = userMapper.findByUsername(dto.getUsername());
        if (existingUser != null) {
            // 这里抛出的异常会被你的 GlobalExceptionHandler 捕获并返回给前端
            throw new RuntimeException("工号 [" + dto.getUsername() + "] 已存在，请检查后重新输入");
        }

        // 1. 先在 User 表创建账号
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());

        // 密码处理
        String rawPassword = (dto.getPassword() == null || dto.getPassword().isEmpty()) ? "123456" : dto.getPassword();
        user.setPassword(rawPassword);

        user.setUserType(UserType.TEACHER); // 教师类型：2
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // 插入 User 记录
        userMapper.insert(user); // MyBatis Plus 或 XML 配置了 useGeneratedKeys 后会回填 ID

        // 2. 再在 Teacher 表创建信息
        Teacher teacher = new Teacher();
        // 这里的 user.getId() 必须确保能够获取到，如果获取不到说明 Mapper 配置有问题
        teacher.setUserId(user.getId());
        teacher.setTitle(dto.getTitle());
        teacher.setIntroduction(dto.getIntroduction());

        teacherMapper.insert(teacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TeacherDTO dto) {
        // 1. 更新 User 表 (姓名)
        User user = new User();
        user.setId(dto.getUserId());
        user.setName(dto.getName());
        userMapper.updateName(user);

        // 2. 更新 Teacher 表 (职称、简介)
        Teacher teacher = new Teacher();
        teacher.setId(dto.getId());
        teacher.setTitle(dto.getTitle());
        teacher.setIntroduction(dto.getIntroduction());
        teacherMapper.update(teacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, Long userId) {
        // 先删外键表 (teacher)
        teacherMapper.deleteById(id);
        // 再删主表 (user)
        userMapper.deleteById(userId);
    }
}