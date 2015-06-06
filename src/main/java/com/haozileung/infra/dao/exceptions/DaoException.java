package com.haozileung.infra.dao.exceptions;

import com.haozileung.infra.dao.enums.IEnum;

/**
 * 自定义异常类
 */
public class DaoException extends RuntimeException {

	/**
     *
     */
	private static final long serialVersionUID = 8654030789221463350L;

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
	public DaoException() {
		super();
	}

	/**
	 * Instantiates a new DexcoderException.
	 *
	 * @param e
	 *            the e
	 */
	public DaoException(IEnum e) {
		super(e.getDesc());
		this.resultCode = e.getCode();
		this.resultMsg = e.getDesc();
	}

	/**
	 * Instantiates a new DexcoderException.
	 *
	 * @param e
	 *            the e
	 */
	public DaoException(Throwable e) {
		super(e);
		this.resultMsg = e.getMessage();
	}

	/**
	 * Constructor
	 *
	 * @param message
	 *            the message
	 */
	public DaoException(String message) {
		super(message);
		this.resultMsg = message;
	}

	/**
	 * Constructor
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 */
	public DaoException(String code, String message) {
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
