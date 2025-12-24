package cn.usst.service;
import cn.usst.pojo.Course;
import cn.usst.pojo.PageResult;
import cn.usst.pojo.dto.CourseDTO;
import cn.usst.pojo.dto.QueryDTO;

public interface AdCourseService {
    PageResult<Course> page(QueryDTO queryDTO);
    void add(CourseDTO dto);
    void update(CourseDTO dto);
    void delete(Long id);
}