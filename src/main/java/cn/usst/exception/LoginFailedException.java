package cn.usst.exception;

/**
 * 登录失败业务异常
 */
public class LoginFailedException extends RuntimeException {

    private final String message;

    public LoginFailedException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}


