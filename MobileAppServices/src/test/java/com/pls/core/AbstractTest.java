package com.pls.core;

import java.lang.reflect.Field;

public abstract class AbstractTest<T> {

	protected void setField(String emFieldName, Object value) throws Exception {
        Class c = bean.getClass();

        // get the reflected object 
        Field field = c.getDeclaredField(emFieldName);
        // set accessible true 
        field.setAccessible(true);
        // modify the member variable
        field.set(bean, value);
	}
	
	protected T bean;
}
