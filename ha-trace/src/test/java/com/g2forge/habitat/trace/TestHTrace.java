package com.g2forge.habitat.trace;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.trace.executable.IExecutable;

public class TestHTrace {
	public static class InitializerInline {
		protected final int a = 0;
		protected final IStackTraceAnalyzer field = new ThrowableStackTraceAnalyzer(this);
		protected final int b = 0;
	}

	public static class InitializerMethod {
		protected final int a;
		protected final IStackTraceAnalyzer field;
		protected final int b;

		public InitializerMethod() {
			a = 0;
			field = new ThrowableStackTraceAnalyzer(this);
			b = 0;
		}
	}

	public static class InitializerMultiline {
		protected final int a = 0;
		// @formatter:off
		protected final IStackTraceAnalyzer field =
				new ThrowableStackTraceAnalyzer(
						this);
		// @formatter:on
		protected final int b;

		public InitializerMultiline() {
			b = 0;
		}
	}

	public static class InitializerMultiple {
		protected final IStackTraceAnalyzer field;

		public InitializerMultiple(boolean b) {
			field = new ThrowableStackTraceAnalyzer(this);
		}

		public InitializerMultiple(int x) {
			field = new ThrowableStackTraceAnalyzer(this);
		}
	}

	public static class Multiple {
		public IExecutable m(int[] i) {
			return HTrace.getCaller();
		}

		public IExecutable m(Integer[] i) {
			return HTrace.getCaller();
		}
	}

	protected static final IExecutable staticEntrypointExecutable;

	protected static final Throwable staticEntrypointThrowable;

	static {
		IExecutable _staticEntrypointExecutable = null;
		Throwable _staticEntrypointThrowable = null;
		try {
			_staticEntrypointExecutable = HTrace.getEntrypoint(EntrypointFilter.ALL);
		} catch (Throwable throwable) {
			_staticEntrypointThrowable = throwable;
		}
		staticEntrypointExecutable = _staticEntrypointExecutable;
		staticEntrypointThrowable = _staticEntrypointThrowable;
	}

	@Test
	public void caller() {
		final IExecutable executable = HTrace.getCaller();
		HAssert.assertEquals("caller", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}

	@Test
	public void entrypoint() {
		final IExecutable executable = HTrace.getEntrypoint(EntrypointFilter.ALL);
		HAssert.assertEquals("entrypoint", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}

	@Test
	public void initializerInline() {
		final List<? extends ISmartStackTraceElement> elements = new InitializerInline().field.getElements();
		final Field field = elements.get(1).getInitialized();
		HAssert.assertNotNull(field);
		HAssert.assertEquals("field", field.getName());
	}

	@Test
	public void initializerMethod() {
		final List<? extends ISmartStackTraceElement> elements = new InitializerMethod().field.getElements();
		final Field field = elements.get(1).getInitialized();
		HAssert.assertNotNull(field);
		HAssert.assertEquals("field", field.getName());
	}

	@Test
	public void initializerMultiline() {
		final List<? extends ISmartStackTraceElement> elements = new InitializerMultiline().field.getElements();
		final Field field = elements.get(1).getInitialized();
		HAssert.assertNotNull(field);
		HAssert.assertEquals("field", field.getName());
	}

	@Test
	public void initializerMultiple() {
		final List<? extends ISmartStackTraceElement> elements = new InitializerMultiple(false).field.getElements();
		final Field field = elements.get(1).getInitialized();
		HAssert.assertNotNull(field);
		HAssert.assertEquals("field", field.getName());
	}

	@Test
	public void mainThread() {
		final Thread mainThread = HTrace.getMainThread();
		final Thread currentThread = Thread.currentThread();
		// If the main thread is current, there's nothing more to test
		if (mainThread != currentThread) {
			// Main thread isn't current, so make sure we're starting form Thread.run
			final IExecutable entrypoint = new ThreadStackTraceAnalyzer(currentThread, 0).getEntrypoint(HCollection.emptySet());
			HAssert.assertEquals(Thread.class, entrypoint.getDeclaringClass());
			HAssert.assertEquals("run", entrypoint.getName());
		}
	}

	@Test
	public void object() {
		HAssert.assertEquals(Integer.class, new Multiple().m((Integer[]) null).getExecutable().getParameterTypes()[0].getComponentType());
	}

	@Test
	public void primitive() {
		HAssert.assertEquals(Integer.TYPE, new Multiple().m((int[]) null).getExecutable().getParameterTypes()[0].getComponentType());
	}

	@Test
	public void staticEntrypoint() {
		if (staticEntrypointThrowable != null) throw new AssertionError(staticEntrypointThrowable);
		HAssert.assertNotNull(staticEntrypointExecutable);
		HAssert.assertEquals(getClass(), staticEntrypointExecutable.getDeclaringClass());
	}
}
