package com.haozileung.infra.dal.exceptions;

/**
 * Assistant 自定义异常类
 * <p/>
 * Created by liyd on 6/27/14.
 */
public class AssistantException extends RuntimeException {

    private static final long serialVersionUID = -3039546280700778038L;

    /**
     * Exception code
     */
    protected String resultCode = "UN_KNOWN_EXCEPTION";

    /**
     * Exception message
     */
    protected String resultMsg = "未知异常";

    /**
     * Constructor
     */
    public AssistantException() {
        super();
    }

    public AssistantException(String message, Throwable e) {
        super(message, e);
        this.resultMsg = message;
    }

    /**
     * Instantiates a new AssistantException.
     *
     * @param e the e
     */
    public AssistantException(Throwable e) {
        super(e);
        this.resultMsg = e.getMessage();
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public AssistantException(String message) {
        super(message);
        this.resultMsg = message;
    }

    /**
     * Constructor
     *
     * @param code    the code
     * @param message the message
     */
    public AssistantException(String code, String message) {
        super(message);
        this.resultCode = code;
        this.resultMsg = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
