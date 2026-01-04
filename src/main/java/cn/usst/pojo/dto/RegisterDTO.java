package cn.usst.pojo.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String name; // 姓名
}