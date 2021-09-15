package com.g2forge.habitat.trace;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;

public interface ISmartStackTraceElement extends IStackTraceElement {
	public Class<?> getDeclaringClass();

	public Executable getExecutable();

	public Field getInitialized();
}
