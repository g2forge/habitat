package com.g2forge.habitat.trace;

import java.lang.reflect.Method;

public interface ISmartStackTraceElement extends IStackTraceElement {
	public Class<?> getDeclaringClass();

	public Method getMethod();
}
