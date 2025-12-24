package cn.usst.controller;

import cn.usst.pojo.PageResult;
import cn.usst.pojo.Result;
import cn.usst.pojo.dto.QueryDTO;
import cn.usst.pojo.dto.TeacherDTO;
import cn.usst.service.AdTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/teacher")
public class AdTeacherController {

    @Autowired
    private AdTeacherService teacherService;

    @GetMapping("/list")
    public Result list(QueryDTO queryDTO) {
        log.info("分页查询教师列表: {}", queryDTO);
        PageResult<TeacherDTO> pageResult = teacherService.page(queryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result add(@RequestBody TeacherDTO dto) {
        log.info("新增教师: {}", dto);
        teacherService.add(dto);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody TeacherDTO dto) {
        log.info("修改教师: {}", dto);
        teacherService.update(dto);
        return Result.success();
    }

    // 删除时需要同时传递 teacherId 和 userId
    @DeleteMapping("/{id}/{userId}")
    public Result delete(@PathVariable Long id, @PathVariable Long userId) {
        log.info("删除教师 teacherId={}, userId={}", id, userId);
        teacherService.delete(id, userId);
        return Result.success();
    }
}