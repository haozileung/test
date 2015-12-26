package com.haozileung.infra.exceptions;

import com.haozileung.infra.enums.IEnum;

/**
 * 自定义异常类
 * <p/>
 * Created by liyd on 6/27/14.
 */
public class MyException extends RuntimeException {

    private static final long serialVersionUID = -3039546280700778038L;

    /**
     * Exception code
     */
    protected String          resultCode       = "UN_KNOWN_EXCEPTION";

    /**
     * Exception message
     */
    protected String          resultMsg        = "未知异常";

    /**
     * Instantiates a new DexcoderException.
     *
     * @param e the e
     */
    public MyException(IEnum e) {
        super(e.getDesc());
        this.resultCode = e.getCode();
        this.resultMsg = e.getDesc();
    }

    public MyException(String message, Throwable e) {
        super(message, e);
        this.resultMsg = message;
    }

    /**
     * Instantiates a new DexcoderException.
     *
     * @param e the e
     */
    public MyException(Throwable e) {
        super(e);
        this.resultMsg = e.getMessage();
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public MyException(String message) {
        super(message);
        this.resultMsg = message;
    }

    /**
     * Constructor
     *
     * @param code    the code
     * @param message the message
     */
    public MyException(String code, String message) {
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
