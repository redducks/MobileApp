package com.pls.core.util;

import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

import com.pls.core.exceptions.ExceptionCodes;
import com.pls.core.exceptions.PLSException;

public class ErrorCodeMatcher extends TypeSafeMatcher<PLSException> {

	private final int errorCode;

	public ErrorCodeMatcher(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public ErrorCodeMatcher(ExceptionCodes errorCode) {
		this.errorCode = errorCode.getCode();
	}

	public void describeTo(Description description) {
		description.appendText("Exception has SQL error code ");
		description.appendText(String.valueOf(errorCode));
	}

	@Override
	public boolean matchesSafely(PLSException exception) {
		return errorCode == exception.getErrorCode();
	}

	public static ErrorCodeMatcher hasErrorCode(ExceptionCodes errorCode) {
		return new ErrorCodeMatcher(errorCode);
	}
	
	public static ErrorCodeMatcher hasErrorCode(int errorCode) {
		return new ErrorCodeMatcher(errorCode);
	}
}
