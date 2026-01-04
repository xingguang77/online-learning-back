package cn.usst.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class ResourcePostDTO {
    private Long id;
    private String title;
    private String description;
    private Long courseId;
    private Long classId;
    // 权限设置：1-all 或 0-class_only
    private Integer visibility;
    // 附件URL列表
    private List<String> fileUrls;
}