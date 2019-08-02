package com.g2forge.habitat.trace;

public interface IStackTraceElement {
	public String getClassName();

	public String getFileName();

	public int getLineNumber();

	public String getMethodName();
}
