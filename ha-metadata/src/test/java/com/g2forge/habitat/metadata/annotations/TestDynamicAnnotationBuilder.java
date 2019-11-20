package com.g2forge.habitat.metadata.annotations;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;

public class TestDynamicAnnotationBuilder {
	public @interface A {
		public String value();
	}

	@Test
	public void test() {
		final A a = new DynamicAnnotationBuilder<A>(A.class).add(A::value, "Hello").build();
		HAssert.assertEquals("Hello", a.value());
	}
}
