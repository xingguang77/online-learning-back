package cn.usst.controller;

import cn.usst.pojo.Course;
import cn.usst.pojo.PageResult;
import cn.usst.pojo.Result;
import cn.usst.pojo.dto.CourseDTO;
import cn.usst.pojo.dto.QueryDTO;
import cn.usst.service.AdCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/course")
public class AdCourseController {

    @Autowired
    private AdCourseService adCourseService;

    @GetMapping("/list")
    public Result list(QueryDTO queryDTO) {
        log.info("分页查询: {}", queryDTO);
        PageResult<Course> pageResult = adCourseService.page(queryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result add(@RequestBody CourseDTO dto) {
        log.info("新增课程: {}",dto);
        adCourseService.add(dto);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody CourseDTO dto) {
        log.info("修改课程: {}",dto);
        adCourseService.update(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        log.info("删除id为{}的课程",id);
        adCourseService.delete(id);
        return Result.success();
    }
}