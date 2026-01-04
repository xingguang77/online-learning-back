package cn.usst.pojo.dto;

import lombok.Data;

@Data
public class QuestionSearchDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String keyword;     // 搜 标题、问题内容、回答内容
    private Long courseId;      // 搜 课程
    private String teacherName; // [新增] 搜 教师姓名
    private Integer status;     // 搜 状态
}