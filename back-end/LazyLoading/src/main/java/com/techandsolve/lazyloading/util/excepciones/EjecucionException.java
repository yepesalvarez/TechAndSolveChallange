package com.techandsolve.lazyloading.util.excepciones;

public class EjecucionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3965606892809179806L;

	public EjecucionException() {
		super();
	}

	public EjecucionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EjecucionException(String message, Throwable cause) {
		super(message, cause);
	}

	public EjecucionException(String message) {
		super(message);
	}

	public EjecucionException(Throwable cause) {
		super(cause);
	}

}
