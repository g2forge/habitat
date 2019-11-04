package com.g2forge.habitat.metadata.v2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.v2.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;

public class TestRepeatableAnnotationMetadata {
	@Contained("A")
	public interface Annotated1 {}

	@Contained("A")
	@Contained("B")
	public interface Annotated2 {}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Repeatable(Container.class)
	public @interface Contained {
		public String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Container {
		public Contained[] value();
	}

	@Test
	public void annotated1() {
		final ISubject subject = Metadata.getStandard().of(Annotated1.class);

		final IPredicate<Contained> contained = subject.bind(Contained.class);
		HAssert.assertTrue(contained.isPresent());
		HAssert.assertEquals("A", contained.get0().value());

		final IPredicate<Container> container = subject.bind(Container.class);
		HAssert.assertTrue(container.isPresent());
		HAssert.assertEquals(HCollection.asList("A"), Stream.of(container.get0().value()).map(Contained::value).collect(Collectors.toList()));
	}

	@Test
	public void annotated2() {
		final ISubject subject = Metadata.getStandard().of(Annotated2.class);

		final IPredicate<Contained> contained = subject.bind(Contained.class);
		HAssert.assertNull(contained.get0());
		HAssert.assertFalse(contained.isPresent());

		final IPredicate<Container> container = subject.bind(Container.class);
		HAssert.assertTrue(container.isPresent());
		HAssert.assertEquals(HCollection.asList("A", "B"), Stream.of(container.get0().value()).map(Contained::value).collect(Collectors.toList()));
	}
}
