package com.g2forge.habitat.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.subject.ISubject;

public class TestMergedMetadata {
	@Retention(RetentionPolicy.RUNTIME)
	public @interface A {
		public String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface B {
		public String value();
	}

	@A("A")
	public static class C {}

	@B("B")
	public static class D {}

	@Test
	public void test() {
		final IMetadataContext metadata = HMetadata.getStandard();
		final ISubject subject = metadata.merge(metadata.of(C.class), metadata.of(D.class));
		HAssert.assertTrue(subject.isPresent(A.class));
		HAssert.assertTrue(subject.isPresent(B.class));
	}
}
