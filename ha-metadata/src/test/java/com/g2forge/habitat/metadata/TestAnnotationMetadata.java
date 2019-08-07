package com.g2forge.habitat.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;

public class TestAnnotationMetadata {
	@Retained("Hello")
	@NotRetained("Hello")
	public static class Annotated {}

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
		final IMetadataContext metadata = HMetadata.getStandard();
		final Retained a = metadata.of(Annotated.class).get(Retained.class);
		final Retained b = metadata.of(null, a).get(Retained.class);
		HAssert.assertEquals("Meta", b.value());
	}

	@Test(expected = IllegalArgumentException.class)
	public void notRetained() {
		HMetadata.getStandard().of(Annotated.class).get(NotRetained.class);
	}

	@Test
	public void present() {
		final IMetadataContext metadata = HMetadata.getStandard();
		HAssert.assertTrue(metadata.of(Annotated.class).isPresent(Retained.class));
		HAssert.assertFalse(metadata.of(TestAnnotationMetadata.class).isPresent(Retained.class));
	}

	@Test
	public void retained() {
		final Retained value = HMetadata.getStandard().of(Annotated.class).get(Retained.class);
		HAssert.assertEquals("Hello", value.value());
	}
}
