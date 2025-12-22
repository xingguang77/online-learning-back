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

    /** 接收用户 user.id */
    private Long userId;

    /** answer */
    private String type;

    private Long questionId;

    private Long answerId;

    /** 0未读 1已读 */
    private Integer isRead;

    private LocalDateTime createTime;
}
