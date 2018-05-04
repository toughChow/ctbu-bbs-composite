package bbs.base.lang;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -7443213283905815106L;
    private int code;

    public BaseException() {
    }

    /**
     * MtonsException
     *
     * @param code 错误代码
     */
    public BaseException(int code) {
        super("code=" + code);
        this.code = code;
    }

    /**
     * MtonsException
     *
     * @param message 错误消息
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * MtonsException
     *
     * @param cause 捕获的异常
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * MtonsException
     *
     * @param message 错误消息
     * @param cause   捕获的异常
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * MtonsException
     *
     * @param code    错误代码
     * @param message 错误消息
     */
    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
