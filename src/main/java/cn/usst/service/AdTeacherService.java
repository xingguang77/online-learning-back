package cn.usst.service;

import cn.usst.pojo.PageResult;
import cn.usst.pojo.dto.QueryDTO; // 复用查询DTO，或者你可以新建一个 TeacherQueryDTO
import cn.usst.pojo.dto.TeacherDTO;

public interface AdTeacherService {
    // 分页查询
    PageResult<TeacherDTO> page(QueryDTO queryDTO);

    // 新增教师（双表）
    void add(TeacherDTO dto);

    // 修改教师（双表）
    void update(TeacherDTO dto);

    // 删除教师（双表）
    void delete(Long id, Long userId);
}