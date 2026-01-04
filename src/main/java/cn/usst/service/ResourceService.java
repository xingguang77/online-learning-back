package cn.usst.service;

import cn.usst.pojo.Resource;
import cn.usst.pojo.dto.ClazzVO;
import cn.usst.pojo.dto.ResourceDTO;
import cn.usst.pojo.dto.ResourcePostDTO;
import cn.usst.pojo.dto.ResourceSearchDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ResourceService {
    // 学生分页浏览
    PageInfo<ResourceDTO> getResourcesForStudent(ResourceSearchDTO dto, Long studentId);

    // 学生上传资源
    void studentUpload(ResourcePostDTO dto, Long studentId);

    // 获取详情
    ResourceDTO getResourceDetail(Long id);

    // 下载计数
    void recordDownload(Long id);

    // 管理员：获取资源列表
    PageInfo<ResourceDTO> getResourcesForAdmin(ResourceSearchDTO dto);

    // 管理员：审核或更新状态 (pass/reject/delete)
//    void updateResourceStatus(Long id, Integer status);

    void updateResource(Resource resource);

    void updateResourceStatus(Long id, Integer i);

    List<ClazzVO> getStudentClasses(Long studentId);

    void deleteResourceByStudent(Long id, Long studentId);

    void updateResourceByStudent(ResourcePostDTO dto, Long studentId);

    PageInfo<ResourceDTO> getStudentOwnResources(ResourceSearchDTO dto, Long studentId);

    ClazzVO getClassById(Long id);
}