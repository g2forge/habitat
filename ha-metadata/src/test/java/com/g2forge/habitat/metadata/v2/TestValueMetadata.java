package com.g2forge.habitat.metadata.v2;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.v2.examples.Annotated;
import com.g2forge.habitat.metadata.v2.examples.Retained;

public class TestValueMetadata {
	@Test
	public void meta() {
		final IMetadata metadata = Metadata.getStandard();
		final Retained retained = metadata.of(Annotated.class).get(Retained.class);
		HAssert.assertException(Throwable.class, () -> metadata.of(retained));
	}
}
