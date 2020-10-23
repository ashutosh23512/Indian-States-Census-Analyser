package com.census;

public class CensusException extends Exception {

	enum ExceptionType {
		WRONG_CSV, WRONG_TYPE, WRONG_HEADER, UNABLE_TO_PARSE, NO_CENSUS_DATA;
	}

	ExceptionType type;

	public CensusException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}

}
