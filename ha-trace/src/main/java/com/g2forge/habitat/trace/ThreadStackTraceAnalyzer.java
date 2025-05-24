package com.g2forge.habitat.trace;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ThreadStackTraceAnalyzer extends AStackTraceAnalyzer {
	protected final ClassLoader contextClassloader;

	protected final StackTraceElement[] stackTrace;

	protected final int invisibles;

	public ThreadStackTraceAnalyzer(Thread thread, int invisibles) {
		this.contextClassloader = thread.getContextClassLoader();
		this.stackTrace = thread.getStackTrace();
		this.invisibles = invisibles;
	}
}
