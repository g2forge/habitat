package com.g2forge.habitat.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;

/**
 * This is similar to {@link TestRepeatableAnnotationMetadata} except that {@link TestInheritedAnnotationMetadata.Contained} is {@link Inherited}.
 */
public class TestInheritedAnnotationMetadata {
	@Contained("AC")
	@NonRepeatable("ANR")
	public interface A {}

	@Contained("BC")
	@NonRepeatable("BNR")
	public interface B extends A {}

	public interface C extends A {}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Repeatable(Container.class)
	@Inherited
	public @interface Contained {
		public String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public @interface Container {
		public Contained[] value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Inherited
	public @interface NonRepeatable {
		public String value();
	}

	@Test
	public void aContained() {
		final IPredicate<Contained> contained = Metadata.getStandard().of(A.class).bind(Contained.class);
		HAssert.assertTrue(contained.isPresent());
		HAssert.assertEquals("AC", contained.get().value());
	}

	@Test
	public void aContainer() {
		final IPredicate<Container> container = Metadata.getStandard().of(A.class).bind(Container.class);
		HAssert.assertTrue(container.isPresent());
		HAssert.assertEquals(HCollection.asList("AC"), Stream.of(container.get().value()).map(Contained::value).collect(Collectors.toList()));
	}

	@Test
	public void aNonRepeatable() {
		final IPredicate<NonRepeatable> nonRepeatable = Metadata.getStandard().of(A.class).bind(NonRepeatable.class);
		HAssert.assertTrue(nonRepeatable.isPresent());
		HAssert.assertEquals("ANR", nonRepeatable.get().value());
	}

	@Test
	public void bContained() {
		final IPredicate<Contained> contained = Metadata.getStandard().of(B.class).bind(Contained.class);
		HAssert.assertTrue(contained.isPresent());
		HAssert.assertEquals("BC", contained.get().value());
	}

	@Test
	public void bContainer() {
		final IPredicate<Container> container = Metadata.getStandard().of(B.class).bind(Container.class);
		HAssert.assertTrue(container.isPresent());
		HAssert.assertEquals(HCollection.asList("BC", "AC"), Stream.of(container.get().value()).map(Contained::value).collect(Collectors.toList()));
	}

	@Test
	public void bNonRepeatable() {
		final IPredicate<NonRepeatable> nonRepeatable = Metadata.getStandard().of(B.class).bind(NonRepeatable.class);
		HAssert.assertTrue(nonRepeatable.isPresent());
		HAssert.assertEquals("BNR", nonRepeatable.get().value());
	}

	@Test
	public void cContained() {
		final IPredicate<Contained> contained = Metadata.getStandard().of(C.class).bind(Contained.class);
		HAssert.assertTrue(contained.isPresent());
		HAssert.assertEquals("AC", contained.get().value());
	}

	@Test
	public void cContainer() {
		final IPredicate<Container> container = Metadata.getStandard().of(C.class).bind(Container.class);
		HAssert.assertTrue(container.isPresent());
		HAssert.assertEquals(HCollection.asList("AC"), Stream.of(container.get().value()).map(Contained::value).collect(Collectors.toList()));
	}

	@Test
	public void cNonRepeatable() {
		final IPredicate<NonRepeatable> nonRepeatable = Metadata.getStandard().of(C.class).bind(NonRepeatable.class);
		HAssert.assertTrue(nonRepeatable.isPresent());
		HAssert.assertEquals("ANR", nonRepeatable.get().value());
	}
}
