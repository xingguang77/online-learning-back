package cn.usst.service.impl;

import cn.usst.mapper.AdClazzMapper;
import cn.usst.pojo.Clazz;
import cn.usst.pojo.PageResult;
import cn.usst.pojo.dto.ClazzDTO;
import cn.usst.pojo.dto.ClazzQueryDTO;
import cn.usst.pojo.dto.ClazzVO;
import cn.usst.service.AdClazzService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdClazzServiceImpl implements AdClazzService {

    @Autowired
    private AdClazzMapper adClazzMapper;

    @Override
    public PageResult<ClazzVO> page(ClazzQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        List<ClazzVO> list = adClazzMapper.selectList(queryDTO);
        Page<ClazzVO> p = (Page<ClazzVO>) list;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void add(ClazzDTO dto) {
        // 校验唯一性：CourseId + TeacherId
        checkUnique(dto.getCourseId(), dto.getTeacherId(), null);
        Clazz clazz = new Clazz();
        clazz.setCourseId(dto.getCourseId());
        clazz.setClassName(dto.getClassName());
        clazz.setTeacherId(dto.getTeacherId());
        clazz.setCreateTime(LocalDateTime.now());
        adClazzMapper.insert(clazz);
    }

    @Override
    public void update(ClazzDTO dto) {
        // 校验唯一性：CourseId + TeacherId
        checkUnique(dto.getCourseId(), dto.getTeacherId(), dto.getId());
        Clazz clazz = new Clazz();
        clazz.setId(dto.getId());
        clazz.setCourseId(dto.getCourseId());
        clazz.setClassName(dto.getClassName());
        clazz.setTeacherId(dto.getTeacherId());
        adClazzMapper.update(clazz);
    }

    @Override
    public void delete(Long id) {
        adClazzMapper.deleteById(id);
    }

    // 核心校验逻辑
    private void checkUnique(Long courseId, Long teacherId, Long excludeId) {
        int count = adClazzMapper.countByCourseAndTeacher(courseId, teacherId, excludeId);
        if (count > 0) {
            throw new RuntimeException("操作失败：该教师已担任该课程的授课老师，不可重复分配！");
        }
    }
}