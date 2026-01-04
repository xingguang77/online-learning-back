package cn.usst.service.impl;

import cn.usst.mapper.ResourceMapper;
import cn.usst.pojo.Resource;
import cn.usst.pojo.dto.ClazzVO;
import cn.usst.pojo.dto.ResourceDTO;
import cn.usst.pojo.dto.ResourcePostDTO;
import cn.usst.pojo.dto.ResourceSearchDTO;
import cn.usst.service.ResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public PageInfo<ResourceDTO> getResourcesForStudent(ResourceSearchDTO dto, Long studentId) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<ResourceDTO> list = resourceMapper.selectResourcesForStudent(dto, studentId);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void studentUpload(ResourcePostDTO dto, Long studentId) {
        // 1. 校验：学生必须属于该班级
        if (dto.getClassId() != null) {
            Integer count = resourceMapper.checkStudentClass(studentId, dto.getClassId());
            if (count == 0) {
                throw new RuntimeException("您不属于该班级，无法发布资源");
            }
        }

        // 2. 插入资源主体
        Resource resource = new Resource();
        if (dto.getCourseId() == null && dto.getClassId() != null) {
            // 建议在 mapper 中加一个根据 classId 查 courseId 的方法
            Long cid = resourceMapper.getCourseIdByClassId(dto.getClassId());
            resource.setCourseId(cid);
        } else {
            resource.setCourseId(dto.getCourseId());
        }
        resource.setTitle(dto.getTitle());
        resource.setDescription(dto.getDescription());
        resource.setClassId(dto.getClassId());
        resource.setUserId(studentId);
        resource.setUploaderRole("student");
        resource.setVisibility(dto.getVisibility());
        // 学生上传的资源默认为 0-正常
        resource.setStatus(1);

        resourceMapper.insertResource(resource);

        // 3. 插入附件
        if (dto.getFileUrls() != null && !dto.getFileUrls().isEmpty()) {
            resourceMapper.insertResourceFiles(resource.getId(), dto.getFileUrls());
        }
    }

    @Override
    public ResourceDTO getResourceDetail(Long id) {
        return resourceMapper.selectResourceById(id);
    }

    @Override
    public void recordDownload(Long id) {
        resourceMapper.incrementDownloadCount(id);
    }

    @Override
    public PageInfo<ResourceDTO> getResourcesForAdmin(ResourceSearchDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<ResourceDTO> list = resourceMapper.selectResourcesForAdmin(dto);
        return new PageInfo<>(list);
    }

    @Override
    public void updateResource(Resource resource) {
        // 这里只更新标题和描述，防止修改关键字段如 uploader_id
        resourceMapper.updateResourceContent(resource);
    }

    @Override
    public void updateResourceStatus(Long id, Integer status) {
        resourceMapper.updateStatus(id, status);
    }

    @Override
    public List<ClazzVO> getStudentClasses(Long studentId) {
        return resourceMapper.selectStudentClasses(studentId);
    }

    // ResourceServiceImpl.java 实现
    @Override
    public PageInfo<ResourceDTO> getStudentOwnResources(ResourceSearchDTO dto, Long studentId) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        // 需要在 Mapper 新增 selectMyResources
        List<ResourceDTO> list = resourceMapper.selectMyResources(dto, studentId);
        return new PageInfo<>(list);
    }

    @Override
    public ClazzVO getClassById(Long id) {
        return resourceMapper.selectClassById(id);
    }

    @Override
    public void deleteResourceByStudent(Long resourceId, Long studentId) {
        // 1. 校验归属
        Resource r = resourceMapper.selectResourceById(resourceId); // 复用现有查询，注意现有查询返回的是DTO
        // 简单点，直接去数据库查 owner
        Long ownerId = resourceMapper.selectOwnerId(resourceId);

        if (ownerId == null || !ownerId.equals(studentId)) {
            throw new RuntimeException("无权删除此资源或资源不存在");
        }

        // 2. 执行删除 (逻辑删除)
        resourceMapper.updateStatus(resourceId, 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateResourceByStudent(ResourcePostDTO dto, Long studentId) {
        if (dto.getId() == null) throw new RuntimeException("资源ID不能为空");

        // 1. 校验归属
        Long ownerId = resourceMapper.selectOwnerId(dto.getId());
        if (ownerId == null || !ownerId.equals(studentId)) {
            throw new RuntimeException("无权修改此资源");
        }

        // 2. 更新基本信息
        Resource r = new Resource();
        r.setId(dto.getId());
        r.setTitle(dto.getTitle());
        r.setDescription(dto.getDescription());
        r.setVisibility(dto.getVisibility());
        resourceMapper.updateResourceBase(r);

        // 3. 更新附件 (先删后加)
        if (dto.getFileUrls() != null) {
            resourceMapper.deleteResourceFiles(dto.getId());
            if (!dto.getFileUrls().isEmpty()) {
                resourceMapper.insertResourceFiles(dto.getId(), dto.getFileUrls());
            }
        }
    }
}