package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuClazz {

    private Long id;

    /** 学生 user.id */
    private Long studentId;

    /** 班级 id */
    private Long classId;
}

