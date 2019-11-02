package com.g2forge.habitat.metadata.v2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;

public class TestAnnotationMetadata {
	@Retained("Hello")
	@NotRetained("Hello")
	public class Annotated {}

	public @interface NotRetained {
		public String value();
	}

	@Retained("Meta")
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Retained {
		public String value();
	}

	@Test
	public void meta() {
		final IMetadata metadata = Metadata.getStandard();
		HAssert.assertEquals("Meta", metadata.of(Retained.class).get(Retained.class).value());
	}

	@Test(expected = IllegalArgumentException.class)
	public void notRetained() {
		Metadata.getStandard().of(Annotated.class).get(NotRetained.class);
	}

	@Test
	public void present() {
		final IMetadata metadata = Metadata.getStandard();
		HAssert.assertTrue(metadata.of(Annotated.class).isPresent(Retained.class));
		HAssert.assertFalse(metadata.of(TestAnnotationMetadata.class).isPresent(Retained.class));
	}

	@Test
	public void retained() {
		final Retained value = Metadata.getStandard().of(Annotated.class).get(Retained.class);
		HAssert.assertEquals("Hello", value.value());
	}
}
