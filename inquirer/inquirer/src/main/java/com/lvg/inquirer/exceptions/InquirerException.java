package com.lvg.inquirer.exceptions;

public class InquirerException extends Exception {

	private static final long serialVersionUID = 4761964883399197484L;
	
	public InquirerException(String message){
		super(message);
	}

	public InquirerException(Throwable cause){
		super(cause);
	}
	
	public InquirerException(String message, Throwable cause ){
		super(cause);
	}
}
