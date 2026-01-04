package cn.usst.controller;

import cn.usst.pojo.Answer;
import cn.usst.pojo.Question;
import cn.usst.pojo.Result;
import cn.usst.pojo.dto.*;
import cn.usst.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/admin/qa")
public class AdQAController {

    @Autowired
    private QAService qaService;

    /**
     * 1. 获取问题列表
     */
    @GetMapping("/list")
    public Result list(QuestionSearchDTO dto) {
        PageInfo<QuestionDetailDTO> info = qaService.searchQuestions(dto);
        return Result.success(info);
    }

    /**
     * [新增] 1.1 查看问题详情 (包含该问题下的所有回答)
     */
    @GetMapping("/question/{id}")
    public Result getQuestionDetail(@PathVariable Long id) {
        QuestionDetailDTO detail = qaService.getQuestionDetail(id);
        return Result.success(detail);
    }

    /**
     * 2. 删除提问
     */
    @DeleteMapping("/question/{id}")
    public Result deleteQuestion(@PathVariable Long id) {
        qaService.deleteQuestion(id);
        return Result.success("删除成功");
    }

    /**
     * 3. 修改提问内容 (支持附件修改)
     */
    @PutMapping("/question")
    public Result updateQuestion(@RequestBody QuestionUpdateDTO dto) {
        qaService.updateQuestion(dto);
        return Result.success("修改成功");
    }

    /**
     * [新增] 4. 获取单个回答详情 (用于编辑回显)
     */
    @GetMapping("/answer/{id}")
    public Result getAnswer(@PathVariable Long id) {
        AnswerDTO answer = qaService.getAnswerById(id);
        return Result.success(answer);
    }

    /**
     * 5. 修改回答内容 (支持附件修改)
     */
    @PutMapping("/answer")
    public Result updateAnswer(@RequestBody AnswerUpdateDTO dto) {
        qaService.updateAnswer(dto);
        return Result.success("修改回答成功");
    }

    /**
     * 6. 删除回答
     */
    @DeleteMapping("/answer/{id}")
    public Result deleteAnswer(@PathVariable Long id) {
        qaService.deleteAnswer(id);
        return Result.success("删除回答成功");
    }
}