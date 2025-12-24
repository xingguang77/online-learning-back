package cn.usst.mapper;

import cn.usst.pojo.Clazz;
import cn.usst.pojo.dto.ClazzQueryDTO;
import cn.usst.pojo.dto.ClazzVODTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AdClazzMapper {
    // 分页 + 条件查询 (返回 VO)
    List<ClazzVODTO> selectList(ClazzQueryDTO queryDTO);

    // 唯一性校验：查询是否存在相同课程和教师的记录
    // excludeId 用于修改时排除自身
    int countByCourseAndTeacher(@Param("courseId") Long courseId,
                                @Param("teacherId") Long teacherId,
                                @Param("excludeId") Long excludeId);

    // 新增班级
    void insert(Clazz clazz);

    // 更新班级（包括设置老师）
    void update(Clazz clazz);

    // 删除班级
    void deleteById(Long id);


}