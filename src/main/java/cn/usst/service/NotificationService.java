package cn.usst.service;

import cn.usst.pojo.Notification;

public interface NotificationService {
    public Object getUnreadCount(Long currentUserId) ;

    public Object getList(Long currentUserId) ;

    public void readAll(Long currentUserId);

    public void sendNotification(Long userId, String content, Long relatedId);
}
