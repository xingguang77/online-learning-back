package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    private Long id;

    /** 关联 user.id */
    private Long userId;

    /** 职称 */
    private String title;

    /** 教师简介 */
    private String introduction;
}

