package cn.usst.exception;

import cn.usst.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("程序出错啦",e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("程序出错啦",e);
        String message = e.getMessage();
        int i=message.indexOf("Duplicate entry");
        String errMsg=message.substring(i);
        String arr[]=errMsg.split(" ");
        return Result.error(arr[2]+"已存在");
    }

//    @ExceptionHandler
//    public Result handleClazzHasStudentException(ClazzHasStudentsException e) {
//        log.error("程序出错啦",e);
//        return Result.error(e.getMessage());
//    }
//
//    @ExceptionHandler
//    public Result handleDeptHasEmpException(DeptHasEmpException e) {
//        log.error("程序出错啦",e);
//        return Result.error(e.getMessage());
//    }

    @ExceptionHandler
    public Result handleLoginFailedException(LoginFailedException e) {
        log.error("登录失败", e);
        return Result.error(e.getMessage());
    }
}
