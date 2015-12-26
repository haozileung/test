package com.haozileung.infra.exceptions;

import com.haozileung.infra.enums.IEnum;

/**
 * Created by liyd on 2015-12-9.
 */
public class InfraException extends AssistantException {

    private static final long serialVersionUID = 7607256205561930553L;

    public InfraException(String message, Throwable e) {
        super(message, e);
        this.resultMsg = message;
    }

    /**
     * Instantiates a new CommonsAssistantException.
     *
     * @param e the e
     */
    public InfraException(Throwable e) {
        super(e);
    }

    /**
     * Instantiates a new CommonsAssistantException.
     *
     * @param iEnum
     */
    public InfraException(IEnum iEnum) {
        super(iEnum);
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public InfraException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param code    the code
     * @param message the message
     */
    public InfraException(String code, String message) {
        super(code, message);
    }
}
