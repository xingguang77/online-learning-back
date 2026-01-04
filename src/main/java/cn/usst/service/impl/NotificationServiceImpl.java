package cn.usst.service.impl;

import cn.usst.mapper.NotificationMapper;
import cn.usst.pojo.Notification;
import cn.usst.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    // 【修改】接收 relatedId
    public void sendNotification(Long userId, String content, Long relatedId) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setContent(content);
        n.setType(1);
        n.setRelatedId(relatedId); // 设置关联ID
        notificationMapper.insert(n);
    }

    @Override
    public Integer getUnreadCount(Long userId) {
        return notificationMapper.countUnread(userId);
    }

    @Override
    public List<Notification> getList(Long userId) {
        return notificationMapper.selectByUserId(userId);
    }

    @Override
    public void readAll(Long userId) {
        notificationMapper.markAllAsRead(userId);
    }
}