package com.pls.core.util;

public class ObjectUtil {
	public static boolean isEmpty(Object obj) {
		if (obj == null || obj.toString().trim().length() == 0) {
			return true;
		}
		
		return false;
	}
}
