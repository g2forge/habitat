package com.g2forge.habitat.metadata.v2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.v2.TestRepeatableAnnotationMetadata.Contained;
import com.g2forge.habitat.metadata.v2.TestRepeatableAnnotationMetadata.Container;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;

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
	public interface C {}

	@B("B")
	public interface D {}

	@Contained("A")
	public interface E {}

	@Contained("B")
	public interface F {}

	@Test
	public void merged() {
		final IMetadata metadata = Metadata.getStandard();
		final ISubject subject = metadata.merge(metadata.of(C.class), metadata.of(D.class));
		HAssert.assertTrue(subject.isPresent(A.class));
		HAssert.assertTrue(subject.isPresent(B.class));
	}
	
	@Test
	public void repeatable() {
		final IMetadata metadata = Metadata.getStandard();
		final ISubject subject = metadata.merge(metadata.of(E.class), metadata.of(F.class));
		HAssert.assertEquals(HCollection.asList("A", "B"), Stream.of(subject.get(Container.class).value()).map(Contained::value).collect(Collectors.toList()));
	}
}
