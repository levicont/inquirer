package com.lvg.inquirer.exceptions;

public class InvalidDataException extends InquirerException {
	
	private static final long serialVersionUID = 1517562723251397234L;

	public InvalidDataException(String message) {
		super(message);
	}
	
	public InvalidDataException(Throwable cause) {
		super(cause);
	}
	
	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}
}
