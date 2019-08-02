package com.g2forge.habitat.trace;

import java.lang.reflect.Method;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;

public class TestHTrace {
	public static class Multiple {
		public Method m(int[] i) {
			return HTrace.getCaller();
		}

		public Method m(Integer[] i) {
			return HTrace.getCaller();
		}
	}

	@Test
	public void caller() {
		final Method method = HTrace.getCaller();
		HAssert.assertEquals("caller", method.getName());
		HAssert.assertEquals(getClass(), method.getDeclaringClass());
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
