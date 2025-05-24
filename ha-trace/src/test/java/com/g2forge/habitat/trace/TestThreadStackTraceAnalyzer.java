package com.g2forge.habitat.trace;

import java.lang.reflect.Executable;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;

public class TestThreadStackTraceAnalyzer {
	@Test
	public void caller() {
		final Executable executable = new ThreadStackTraceAnalyzer(Thread.currentThread(), 2).getCaller();
		HAssert.assertEquals("caller", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}

	@Test
	public void entrypoint() {
		final Executable executable = new ThreadStackTraceAnalyzer(Thread.currentThread(), 2).getEntrypoint(EntrypointFilter.ALL);
		HAssert.assertEquals("entrypoint", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}
}
