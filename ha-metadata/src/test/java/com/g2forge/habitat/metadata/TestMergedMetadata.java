package com.g2forge.habitat.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.TestRepeatableAnnotationMetadata.Contained;
import com.g2forge.habitat.metadata.TestRepeatableAnnotationMetadata.Container;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;

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

		HAssert.assertEquals("A", subject.get(A.class).value());
		HAssert.assertEquals("B", subject.get(B.class).value());
	}

	@Test
	public void repeatable() {
		final IMetadata metadata = Metadata.getStandard();
		final ISubject subject = metadata.merge(metadata.of(E.class), metadata.of(F.class));
		final IPredicate<Container> predicate = subject.bind(Container.class);
		HAssert.assertTrue(predicate.isPresent());
		HAssert.assertEquals(HCollection.asList("A", "B"), Stream.of(predicate.get0().value()).map(Contained::value).collect(Collectors.toList()));
	}
}
