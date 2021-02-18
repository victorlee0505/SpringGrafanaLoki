package com.example.monitoring.demo.exception;

public class CalculatorException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    

    public CalculatorException(String message) {
		super(message);
	}

	public CalculatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public CalculatorException(Throwable cause) {
		super(cause);
	}
}
