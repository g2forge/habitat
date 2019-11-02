package com.g2forge.habitat.metadata.v2;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.v2.examples.Annotated;
import com.g2forge.habitat.metadata.v2.examples.NotRetained;
import com.g2forge.habitat.metadata.v2.examples.Retained;

public class TestAnnotationMetadata {
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
