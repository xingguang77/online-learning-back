package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

    private Long id;

    /** 资源标题 */
    private String title;

    /** 资源描述 */
    private String description;

    /** 所属课程 */
    private Long courseId;

    /** 班级（本班可见） */
    private Long classId;

    /** 上传者 user.id */
    private Long userId;

    /** student / teacher */
    private String uploaderRole;

    /** all / class_only */
    private String visibility;

    /** 下载次数 */
    private Integer downloadCount;

    /** 1正常 0下架 */
    private Integer status;

    private LocalDateTime createTime;
}

