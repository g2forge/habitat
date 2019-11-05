package com.g2forge.habitat.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;

public class TestValueMetadata {
	@A("A")
	@Retention(RetentionPolicy.RUNTIME)
	public @interface A {
		public String value();
	}

	@A("B")
	public class B {}

	@Test
	public void meta() {
		final IMetadata metadata = Metadata.getStandard();
		final A aOnB = metadata.of(B.class).get(A.class);
		HAssert.assertEquals("B", aOnB.value());
		final A aOnA = metadata.of(aOnB).get(A.class);
		HAssert.assertEquals("A", aOnB.value());
	}

	@Test
	public void override() {

	}

	@Test
	public void value() {

	}

	@Test
	public void element() {

	}
}
