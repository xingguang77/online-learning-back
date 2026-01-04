package cn.usst.mapper;

import cn.usst.pojo.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface NotificationMapper {
    // 插入通知

    void insert(Notification notification);

    // 获取未读数
    @Select("SELECT COUNT(*) FROM notification WHERE user_id = #{userId} AND is_read = 0")
    int countUnread(Long userId);

    // 获取列表
    List<Notification> selectByUserId(Long userId);

    // 标记全部已读
    @Update("UPDATE notification SET is_read = 1 WHERE user_id = #{userId}")
    void markAllAsRead(Long userId);
}