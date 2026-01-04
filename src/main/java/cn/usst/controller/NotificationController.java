package cn.usst.controller;

import cn.usst.pojo.Result;
import cn.usst.service.NotificationService;
import cn.usst.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student/notification")
public class NotificationController {
    @Autowired private NotificationService notificationService;
    @Autowired private HttpServletRequest request;

    private Long getCurrentUserId() {
        String token = request.getHeader("token");
        Claims claims = JwtUtils.parseToken(token);
        return Long.valueOf(claims.get("id").toString());
    }

    // 获取未读数
    @GetMapping("/unread")
    public Result getUnread() {
        return Result.success(notificationService.getUnreadCount(getCurrentUserId()));
    }

    // 获取列表
    @GetMapping("/list")
    public Result getList() {
        return Result.success(notificationService.getList(getCurrentUserId()));
    }

    // 一键已读
    @PutMapping("/read")
    public Result readAll() {
        notificationService.readAll(getCurrentUserId());
        return Result.success();
    }
}