package cn.usst.controller;

import cn.usst.pojo.Result;
import cn.usst.pojo.dto.*;
import cn.usst.service.QAService;
import cn.usst.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/student/qa")
public class StuQAController {

    @Autowired
    private QAService qaService;

    @Autowired
    private HttpServletRequest request;

    private Long getCurrentUserId() {
        String token = request.getHeader("token");
        Claims claims = JwtUtils.parseToken(token);
        return Long.valueOf(claims.get("id").toString());
    }

    /**
     * 3-e 发布问题 (支持文本+图片附件)
     */
    @PostMapping("/add")
    public Result addQuestion(@RequestBody QuestionPostDTO dto) {
        Long studentId = getCurrentUserId();
        qaService.postQuestion(dto, studentId);
        return Result.success("提问成功");
    }

    /**
     * 3-f 问题搜索列表 (全站检索，支持 keyword, courseId)
     */
    @GetMapping("/list")
    public Result list(QuestionSearchDTO dto) {
        PageInfo<QuestionDetailDTO> info = qaService.searchQuestions(dto);
        return Result.success(info);
    }

    /**
     * 查看问题详情 (包含回答)
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        QuestionDetailDTO detail = qaService.getQuestionDetail(id);
        return Result.success(detail);
    }


    /**
     * [新增] g) 个人中心：查询自己提出的问题
     * 同时也支持“查看已被回答的问题”
     */
    @GetMapping("/my")
    public Result myQuestions(QuestionSearchDTO dto) {
        Long studentId = getCurrentUserId();
        // Service 需要新增 getStudentOwnQuestions
        PageInfo<QuestionDetailDTO> page = qaService.getStudentOwnQuestions(dto, studentId);
        return Result.success(page);
    }

    /**
     * [新增] g) 个人中心：删除自己的提问
     */
    @DeleteMapping("/{id}")
    public Result deleteMyQuestion(@PathVariable Long id) {
        Long studentId = getCurrentUserId();
        qaService.deleteQuestionByStudent(id, studentId);
        return Result.success("删除成功");
    }

    /**
     * [新增] g) 个人中心：修改自己的提问
     */
    @PutMapping("/update")
    public Result updateMyQuestion(@RequestBody QuestionUpdateDTO dto) {
        Long studentId = getCurrentUserId();
        qaService.updateQuestionByStudent(dto, studentId);
        return Result.success("修改成功");
    }

}