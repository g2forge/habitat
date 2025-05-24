package com.g2forge.habitat.trace;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ThrowableStackTraceAnalyzer extends AStackTraceAnalyzer {
	protected final Object context;

	protected final Throwable throwable;

	protected final int invisibles;

	public ThrowableStackTraceAnalyzer() {
		this(new Throwable(), 1);
	}

	public ThrowableStackTraceAnalyzer(Object context) {
		this(context, new Throwable(), 1);
	}

	public ThrowableStackTraceAnalyzer(Throwable throwable, int invisibles) {
		this(throwable, throwable, invisibles);
	}

	@Override
	protected ClassLoader getContextClassloader() {
		final ClassLoader retVal = getContext().getClass().getClassLoader();
		if (retVal == null) return Thread.currentThread().getContextClassLoader();
		return retVal;
	}

	@Override
	protected StackTraceElement[] getStackTrace() {
		return getThrowable().getStackTrace();
	}
}
