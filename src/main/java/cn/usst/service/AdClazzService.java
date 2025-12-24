package cn.usst.service;

import cn.usst.pojo.PageResult;
import cn.usst.pojo.dto.ClazzDTO;
import cn.usst.pojo.dto.ClazzQueryDTO;
import cn.usst.pojo.dto.ClazzVODTO;

public interface AdClazzService {
    PageResult<ClazzVODTO> page(ClazzQueryDTO queryDTO);
    void add(ClazzDTO dto);
    void update(ClazzDTO dto);
    void delete(Long id);
}