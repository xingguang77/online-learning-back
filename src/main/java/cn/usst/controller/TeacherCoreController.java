package cn.usst.controller;

import cn.usst.pojo.Result;
import cn.usst.pojo.dto.*;
import cn.usst.service.TeacherCoreService;
import cn.usst.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherCoreController {

    @Autowired
    private TeacherCoreService teacherCoreService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取当前登录用户的ID (辅助方法)
     */
    private Long getCurrentUserId() {
        String token = request.getHeader("token");
        try {
            Claims claims = JwtUtils.parseToken(token);
            return Long.valueOf(claims.get("id").toString());
        } catch (Exception e) {
            throw new RuntimeException("未登录或Token非法");
        }
    }

    /**
     * 1. 教师工作台：获取我教的班级列表（含未回答问题提醒）
     * 对应需求 4-a: 新问题提醒
     */
    @GetMapping("/classes")
    public Result getMyClasses() {
        Long userId = getCurrentUserId();
        List<Map<String, Object>> list = teacherCoreService.getMyClasses(userId);
        return Result.success(list);
    }

    /**
     * 2. 获取某班级的问题列表
     * status 可选: unanswered-0 / answered-1
     */
    @GetMapping("/questions/{classId}")
    public Result getClassQuestions(@PathVariable Long classId,
                                    @RequestParam(required = false) Integer status) {
        List<QuestionDTO> list = teacherCoreService.getClassQuestions(classId, status);
        return Result.success(list);
    }


    /**
     * 2.1 获取教师所教所有课程的问题 (补充需求 4-c: 查看所上课程的全部学生提问)
     */
    @GetMapping("/questions/all")
    public Result getAllMyQuestions() {
        Long userId = getCurrentUserId();
        log.info("教师id: {}", userId);
        List<QuestionDTO> list = teacherCoreService.getAllQuestionsByTeacherId(userId);
        return Result.success(list);
    }

    /**
     * 3. 发布学习资源
     * 对应需求 4-b, 4-d
     */
    @PostMapping("/resource")
    public Result publishResource(@RequestBody ResourcePostDTO dto) {
        Long userId = getCurrentUserId();
        log.info("教师发布资源: {}", dto);
        teacherCoreService.publishResource(dto, userId);
        return Result.success();
    }

    /**
     * 3.1 学科权限设置：修改资源可见性
     * 对应需求 4-d: 设置仅本班可见或全部可见
     * @paramvisibility 0-全部可见, 1-仅本班可见
     */
    @PutMapping("/resource/{resourceId}/visibility")
    public Result updateResourceVisibility(@PathVariable Long resourceId, @RequestBody Map<String, Integer> params) {
        Integer visibility = params.get("visibility");
        teacherCoreService.updateResourceVisibility(resourceId, visibility);
        return Result.success();
    }

    /**
     * 4. 回答学生问题
     * 对应需求 4-c
     */
    @PostMapping("/reply")
    public Result replyQuestion(@RequestBody AnswerPostDTO dto) {
        Long userId = getCurrentUserId();
        log.info("教师回复问题: {}", dto);
        teacherCoreService.replyQuestion(dto, userId);
        return Result.success();
    }

//    /**
//     * 4.1 删除回答 (需求 4-c: 修改或删除自己的回答)
//     */
    @DeleteMapping("/reply/{answerId}")
    public Result deleteAnswer(@PathVariable Long answerId) {
        Long userId = getCurrentUserId();
        teacherCoreService.deleteAnswer(answerId, userId); // 传入userId做权限校验
        return Result.success();
    }
//
//    /**
//     * 4.2 修改回答 (需求 4-c: 修改或删除自己的回答)
//     */
    @PutMapping("/reply")
    public Result updateAnswer(@RequestBody AnswerUpdateDTO dto) {
        Long userId = getCurrentUserId();
        teacherCoreService.updateAnswer(dto, userId);
        return Result.success();
    }

    /**
     * 4.3 获取某个问题的回答详情 (用于回显)
     */
    @GetMapping("/answer/{questionId}")
    public Result getAnswer(@PathVariable Long questionId) {
        AnswerDTO dto = teacherCoreService.getAnswerByQuestionId(questionId);
        return Result.success(dto);
    }
}