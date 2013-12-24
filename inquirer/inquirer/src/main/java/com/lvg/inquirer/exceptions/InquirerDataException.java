package com.lvg.inquirer.exceptions;

public class InquirerDataException extends InquirerException {

	
	private static final long serialVersionUID = 6273692514711160703L;

	public InquirerDataException(String message) {
		super(message);
	}
	
	public InquirerDataException(Throwable cause) {
		super(cause);
	}
	
	public InquirerDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
