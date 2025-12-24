package cn.usst.service.impl;

import cn.usst.mapper.AdCourseMapper;
import cn.usst.pojo.Course;
import cn.usst.pojo.PageResult;
import cn.usst.pojo.dto.CourseDTO;
import cn.usst.pojo.dto.QueryDTO;
import cn.usst.service.AdCourseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdCourseServiceImpl implements AdCourseService {

    @Autowired
    private AdCourseMapper adCourseMapper;

    @Override
    public PageResult<Course> page(QueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        List<Course> courseList = adCourseMapper.list(queryDTO.getName());
        Page<Course> p =(Page<Course>) courseList;
        return new PageResult<>(p.getTotal(),p.getResult());
    }

    @Override
    public void add(CourseDTO dto) {
        Course course = new Course();
        course.setName(dto.getName());
        course.setCollege(dto.getCollege());
        course.setDescription(dto.getDescription());
        adCourseMapper.insert(course);
    }

    @Override
    public void update(CourseDTO dto) {
        Course course = new Course();
        course.setId(dto.getId());
        course.setName(dto.getName());
        course.setCollege(dto.getCollege());
        course.setDescription(dto.getDescription());
        adCourseMapper.update(course);
    }

    @Override
    public void delete(Long id) {
        adCourseMapper.deleteById(id);
    }
}