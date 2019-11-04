package com.g2forge.habitat.metadata;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.IMetadata;
import com.g2forge.habitat.metadata.Metadata;
import com.g2forge.habitat.metadata.TestAnnotationMetadata.Annotated;
import com.g2forge.habitat.metadata.TestAnnotationMetadata.Retained;

public class TestValueMetadata {
	@Test
	public void meta() {
		final IMetadata metadata = Metadata.getStandard();
		final Retained retained = metadata.of(Annotated.class).get(Retained.class);
		HAssert.assertException(Throwable.class, () -> metadata.of(retained));
	}
}
