package com.g2forge.habitat.trace;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.trace.executable.IExecutable;

public class TestThreadStackTraceAnalyzer {
	@Test
	public void caller() {
		final IExecutable executable = new ThreadStackTraceAnalyzer(Thread.currentThread(), 2).getCaller();
		HAssert.assertEquals("caller", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}

	@Test
	public void entrypoint() {
		final IExecutable executable = new ThreadStackTraceAnalyzer(Thread.currentThread(), 2).getEntrypoint(EntrypointFilter.ALL);
		HAssert.assertEquals("entrypoint", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}
}
