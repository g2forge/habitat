package com.g2forge.habitat.trace;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.trace.executable.IExecutable;

public class TestThrowableStackTraceAnalyzer {
	@Test
	public void caller() {
		final IExecutable executable = new ThrowableStackTraceAnalyzer().getCaller();
		HAssert.assertEquals("caller", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}

	@Test
	public void entrypoint() {
		final IExecutable executable = new ThrowableStackTraceAnalyzer().getEntrypoint(EntrypointFilter.ALL);
		HAssert.assertEquals("entrypoint", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}
}
