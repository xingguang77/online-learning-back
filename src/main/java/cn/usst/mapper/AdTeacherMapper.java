package cn.usst.mapper;

import cn.usst.pojo.Teacher;
import cn.usst.pojo.dto.TeacherDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AdTeacherMapper {
    // 联表查询：User + Teacher
    List<TeacherDTO> list(String name);

    void insert(Teacher teacher);

    void update(Teacher teacher);

    void deleteById(Long id);
}