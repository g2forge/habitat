package com.g2forge.habitat.trace;

import java.lang.reflect.Executable;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;

public class TestThrowableStackTraceAnalyzer {
	@Test
	public void caller() {
		final Executable executable = new ThrowableStackTraceAnalyzer().getCaller();
		HAssert.assertEquals("caller", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}

	@Test
	public void entrypoint() {
		final Executable executable = new ThrowableStackTraceAnalyzer().getEntrypoint(EntrypointFilter.ALL);
		HAssert.assertEquals("entrypoint", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}
}
