package cn.usst.controller;

import cn.usst.pojo.Resource;
import cn.usst.pojo.Result;
import cn.usst.pojo.dto.ResourceDTO;
import cn.usst.pojo.dto.ResourceSearchDTO;
import cn.usst.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/admin/resource")
public class AdResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 管理员获取资源列表 (支持按状态筛选，例如查看待审核的)
     */
    @GetMapping("/list")
    public Result list(ResourceSearchDTO dto) {
        PageInfo<ResourceDTO> pageInfo = resourceService.getResourcesForAdmin(dto);
        return Result.success(pageInfo);
    }

    /**
     * 2-c 管理员修改资源详情 (标题、描述)
     * 对应需求：修改资源描述
     */
    @PutMapping("/update")
    public Result updateResourceContent(@RequestBody Resource resource) {
        // 简单校验
        if (resource.getId() == null) {
            return Result.error("资源ID不能为空");
        }
        resourceService.updateResource(resource);
        return Result.success();
    }

    /**
     * 2-c 删除资源 (逻辑删除或物理删除，这里复用状态更新逻辑，设为 0代表删除)
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        // 假设 -1 为删除状态
        resourceService.updateResourceStatus(id, 0);
        return Result.success();
    }
}