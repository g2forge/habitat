package com.g2forge.habitat.metadata.annotations;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;

public class TestDynamicAnnotationBuilder {
	public @interface A {
		public String value();
	}

	public @interface B {
		public String[] value();
	}

	public @interface C {
		public boolean value() default true;
	}

	@Test
	public void a() {
		HAssert.assertEquals("Hello", new DynamicAnnotationBuilder<>(A.class).add(A::value, "Hello").build().value());
	}

	@Test
	public void b() {
		HAssert.assertArrayEquals(new String[] { "Hello", "World!" }, new DynamicAnnotationBuilder<>(B.class).add(B::value, new String[] { "Hello", "World!" }).build().value());
	}

	@Test
	public void c() {
		HAssert.assertTrue(new DynamicAnnotationBuilder<>(C.class).build().value());
	}
}
