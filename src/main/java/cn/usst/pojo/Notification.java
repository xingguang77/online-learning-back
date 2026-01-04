package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Long id;
    private Long userId;
    private String content;
    private Integer type;
    private Integer isRead;
    private LocalDateTime createTime;

    // 【新增】关联ID
    private Long relatedId;
}