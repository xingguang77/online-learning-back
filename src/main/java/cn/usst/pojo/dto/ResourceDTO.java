package cn.usst.pojo.dto;

import cn.usst.pojo.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceDTO extends Resource {
    private String uploaderName; // 上传者姓名
    private String courseName;   // 课程名称
    private List<String> fileUrls; // 附件链接列表
}