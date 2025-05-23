package com.g2forge.habitat.trace;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;

public class TestHTrace {
	public static class InitializerInline {
		protected final int a = 0;
		protected final IStackTraceAnalyzer field = new StackTraceAnalyzer(this);
		protected final int b = 0;
	}

	public static class InitializerMethod {
		protected final int a;
		protected final IStackTraceAnalyzer field;
		protected final int b;

		public InitializerMethod() {
			a = 0;
			field = new StackTraceAnalyzer(this);
			b = 0;
		}
	}

	public static class InitializerMultiline {
		protected final int a = 0;
		// @formatter:off
		protected final IStackTraceAnalyzer field =
				new StackTraceAnalyzer(
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
			field = new StackTraceAnalyzer(this);
		}

		public InitializerMultiple(int x) {
			field = new StackTraceAnalyzer(this);
		}
	}

	public static class Multiple {
		public Executable m(int[] i) {
			return HTrace.getCaller();
		}

		public Executable m(Integer[] i) {
			return HTrace.getCaller();
		}
	}

	@Test
	public void caller() {
		final Executable executable = HTrace.getCaller();
		HAssert.assertEquals("caller", executable.getName());
		HAssert.assertEquals(getClass(), executable.getDeclaringClass());
	}

	@Test
	public void entrypoint() {
		final Executable entrypoint = HTrace.getEntrypoint(EntrypointFilter.ALL);
		HAssert.assertEquals(getClass(), entrypoint.getDeclaringClass());
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
	public void object() {
		HAssert.assertEquals(Integer.class, new Multiple().m((Integer[]) null).getParameterTypes()[0].getComponentType());
	}

	@Test
	public void primitive() {
		HAssert.assertEquals(Integer.TYPE, new Multiple().m((int[]) null).getParameterTypes()[0].getComponentType());
	}
}
