package cn.usst.controller;


import cn.usst.pojo.PageResult;
import cn.usst.pojo.Result;
import cn.usst.pojo.dto.ClazzDTO;
import cn.usst.pojo.dto.ClazzQueryDTO;
import cn.usst.pojo.dto.ClazzVODTO;
import cn.usst.service.AdClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/clazz")
public class AdClazzController {

    @Autowired
    private AdClazzService adClazzService;

    @GetMapping("/list")
    public Result list(ClazzQueryDTO queryDTO) {
        log.info("查询班级列表: {}", queryDTO);
        PageResult<ClazzVODTO> pageResult = adClazzService.page(queryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result add(@RequestBody ClazzDTO dto) {
        adClazzService.add(dto);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody ClazzDTO dto) {
        adClazzService.update(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        adClazzService.delete(id);
        return Result.success();
    }
}