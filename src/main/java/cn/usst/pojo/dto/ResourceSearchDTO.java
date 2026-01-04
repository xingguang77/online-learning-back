package cn.usst.pojo.dto;

import lombok.Data;

@Data
public class ResourceSearchDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String keyword; // 搜索标题
    private Long courseId;  // 按课程筛选
    private Integer status; // 状态筛选 (管理员用)
}