package cn.usst.controller;

import cn.usst.pojo.Result;
import cn.usst.pojo.dto.ClazzVO;
import cn.usst.pojo.dto.ResourceDTO;
import cn.usst.pojo.dto.ResourcePostDTO;
import cn.usst.pojo.dto.ResourceSearchDTO;
import cn.usst.service.ResourceService;
import cn.usst.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

@RestController
@RequestMapping("/student/resource")
public class StuResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private HttpServletRequest request;

    private Long getCurrentUserId() {
        String token = request.getHeader("token");
        Claims claims = JwtUtils.parseToken(token);
        return Long.valueOf(claims.get("id").toString());
    }

    /**
     * 3-b 浏览/搜索学习资源
     */
    @GetMapping("/list")
    public Result list(ResourceSearchDTO dto) {
        Long studentId = getCurrentUserId();
        PageInfo<ResourceDTO> pageInfo = resourceService.getResourcesForStudent(dto, studentId);
        return Result.success(pageInfo);
    }

    /**
     * 3-c 上传学习资料
     */
    @PostMapping("/upload")
    public Result upload(@RequestBody ResourcePostDTO dto) {
        Long studentId = getCurrentUserId();
        resourceService.studentUpload(dto, studentId);
        return Result.success("上传成功");
    }

    /**
     * 3-d 查看资源详情
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ResourceDTO dto = resourceService.getResourceDetail(id);
        return Result.success(dto);
    }

    /**
     * 3-d 下载计数
     */
    @PutMapping("/{id}/download")
    public Result download(@PathVariable Long id) {
        resourceService.recordDownload(id);
        return Result.success();
    }

    /**
     * 3-e 获取学生自己的班级列表 (用于上传资源时选择班级，或筛选资源)
     * 需要在 Service 层实现 getStudentClasses
     */
    @GetMapping("/classes")
    public Result getMyClasses() {
        Long studentId = getCurrentUserId();
        List<ClazzVO> list = resourceService.getStudentClasses(studentId);
        return Result.success(list);
    }

    /**
     * [新增] g) 学生个人中心：查看自己上传的资源
     */
    @GetMapping("/my")
    public Result myResources(ResourceSearchDTO dto) {
        Long studentId = getCurrentUserId();
        // 复用 Service，但在 Service 内部需要处理 "只查自己" 的逻辑
        // 这里我们可以约定：如果 dto 中传入了 userId (在这个场景下由后端强塞)，则只查该用户的
        // 建议在 Service 新增 getStudentOwnResources 方法
        PageInfo<ResourceDTO> page = resourceService.getStudentOwnResources(dto, studentId);
        return Result.success(page);
    }

    /**
     * [新增] g) 学生个人中心：删除自己上传的资源
     */
    @DeleteMapping("/{id}")
    public Result deleteMyResource(@PathVariable Long id) {
        Long studentId = getCurrentUserId();
        resourceService.deleteResourceByStudent(id, studentId);
        return Result.success("删除成功");
    }

    /**
     * [新增] g) 学生个人中心：修改自己上传的资源
     */
    @PutMapping("/update")
    public Result updateMyResource(@RequestBody ResourcePostDTO dto) {
        Long studentId = getCurrentUserId();
        resourceService.updateResourceByStudent(dto, studentId);
        return Result.success("修改成功");
    }

    /**
     * 获取单个班级详情 (用于课程页头部展示：课程名、教师名)
     */
    @GetMapping("/class/{id}")
    public Result getClassDetail(@PathVariable Long id) {
        // 这里复用 resourceService.getStudentClasses 查出列表后在内存过滤，或者写个新SQL
        // 为简单起见，我们假设前端传的是 classId
        // 您需要在 ResourceMapper.xml 中增加 selectClassById
        ClazzVO clazz = resourceService.getClassById(id);
        return Result.success(clazz);
    }
}