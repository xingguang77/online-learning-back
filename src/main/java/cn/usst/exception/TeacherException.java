package cn.usst.exception;


import lombok.Data;


public class TeacherException extends RuntimeException  {
    private String message;

    public TeacherException(String message) {
        super(message);
        this.message = message;
    }

    // get方法（可选，根据需要）
    @Override
    public String getMessage() {
        return message;
    }
}
