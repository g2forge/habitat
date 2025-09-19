package com.g2forge.habitat.trace;

import java.lang.reflect.Field;

import com.g2forge.habitat.trace.executable.IExecutable;

public interface ISmartStackTraceElement extends IStackTraceElement {
	public Class<?> getDeclaringClass();

	public IExecutable getExecutable();

	public Field getInitialized();
}
