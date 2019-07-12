package io.tomahawkd.simpleserver.exceptions;

import java.sql.SQLOutput;

public class MalformedJsonException extends Exception {

	public MalformedJsonException(String message) {
		super(message);
		System.out.println(message);
	}
}
