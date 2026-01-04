package cn.usst.mapper;

import cn.usst.pojo.Resource;
import cn.usst.pojo.dto.ClazzVO;
import cn.usst.pojo.dto.ResourceDTO;
import cn.usst.pojo.dto.ResourceSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ResourceMapper {

    // 学生查询资源列表 (需鉴权：仅看全部可见 OR 本班可见)
    List<ResourceDTO> selectResourcesForStudent(@Param("dto") ResourceSearchDTO dto, @Param("studentId") Long studentId);

    // 管理员查询资源列表 (查看所有)
    List<ResourceDTO> selectResourcesForAdmin(@Param("dto") ResourceSearchDTO dto);

    // 查询资源详情
    ResourceDTO selectResourceById(Long id);

    // 插入资源
    void insertResource(Resource resource);

    // 插入资源附件
    void insertResourceFiles(@Param("resourceId") Long resourceId, @Param("urls") List<String> urls);

    // 增加下载量
    @Update("UPDATE resource SET download_count = download_count + 1 WHERE id = #{id}")
    void incrementDownloadCount(Long id);


    // 检查学生是否在某个班级 (用于上传权限校验)
    Integer checkStudentClass(@Param("studentId") Long studentId, @Param("classId") Long classId);

    // 更新资源状态 (审核/逻辑删除)
    @Update("UPDATE resource SET status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    void updateResourceContent(Resource resource);

    List<ClazzVO> selectStudentClasses(Long studentId);

    // Mapper 接口新增
    List<ResourceDTO> selectMyResources(@Param("dto") ResourceSearchDTO dto, @Param("studentId") Long studentId);
    Long selectOwnerId(Long id);
    void updateResourceBase(Resource resource); // 只更新标题、描述、可见性、状态
    void deleteResourceFiles(Long resourceId);

    ClazzVO selectClassById(Long id);

    @Select("SELECT course_id FROM class WHERE id = #{classId}")
    Long getCourseIdByClassId(Long classId);
}