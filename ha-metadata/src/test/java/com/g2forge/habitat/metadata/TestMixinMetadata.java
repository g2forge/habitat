package com.g2forge.habitat.metadata;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;

import com.g2forge.alexandria.java.type.ref.ITypeRef;
import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.annotations.DynamicAnnotationBuilder;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.IElementSubjectType;
import com.g2forge.habitat.metadata.value.predicate.ConstantPredicate;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.IElementSubject;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public class TestMixinMetadata {
	@B
	public static interface A {}

	@Retention(RetentionPolicy.RUNTIME)
	public static @interface B {}

	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	public static class Element {
		protected final Class<?> type;
	}

	public static class ElementMetadataAccessor implements ITypedMetadataAccessor<Element, IElementSubject, IPredicateType<Element>> {
		@Override
		public IPredicate<Element> bindTyped(IElementSubject subject, IPredicateType<Element> predicateType) {
			return new ConstantPredicate<>(subject, predicateType, new Element((Class<?>) subject.getElement()), true);
		}
	}

	@Test
	public void annotationOnRT() {
		final IMetadata metadata = Metadata.builder().mixins(mixins -> mixins.subject().of(String.class).bind(B.class).set(new DynamicAnnotationBuilder<>(B.class).build()).build()).build();
		HAssert.assertTrue(metadata.of(String.class).isPresent(B.class));
	}

	@Test
	public void mixed() {
		final IMetadata metadata = Metadata.builder().mixins(mixins -> mixins.accessor(new ElementMetadataAccessor()).build()).build();
		final IPredicate<Element> predicate = metadata.of(getClass()).bind(Element.class);
		HAssert.assertEquals(getClass(), predicate.get0().getType());
	}

	@Test
	public void specDirectDirectAbsent() {
		HAssert.assertTrue(Metadata.getStandard().of(A.class).isPresent(B.class));
		final IMetadata metadata = Metadata.builder().mixins(mixins -> mixins.subject().of(A.class).bind(B.class).absent().build()).build();
		HAssert.assertFalse(metadata.of(A.class).isPresent(B.class));
	}

	@Test
	public void specDirectDirectCopy() {
		{ // Present
			final IMetadata metadata = Metadata.builder().mixins(mixins -> {
				mixins.subject().of(A.class).bind(String.class).copy().of(B.class);
				mixins.subject().of(B.class).bind(String.class).set("Hello");
				return mixins.build();
			}).build();
			HAssert.assertEquals("Hello", metadata.of(A.class).get(String.class));
		}

		{ // Absent
			final IMetadata metadata = Metadata.builder().mixins(mixins -> mixins.subject().of(A.class).bind(String.class).copy().of(B.class).build()).build();
			HAssert.assertException(NoAccessorFoundException.class, () -> metadata.of(A.class).isPresent(String.class));
		}
	}

	@Test
	public void specDirectDirectFunctional() {
		final String[] array = new String[1];
		final IMetadata metadata = Metadata.builder().mixins(mixins -> mixins.subject().of(A.class).bind(String.class).supply(() -> array[0]).build()).build();
		HAssert.assertNull(null, metadata.of(A.class).get(String.class));
		array[0] = "Hello";
		HAssert.assertEquals(array[0], metadata.of(A.class).get(String.class));
	}

	@Test
	public void specDirectDirectSet() {
		final IMetadata metadata = Metadata.builder().mixins(mixins -> mixins.subject().of(A.class).bind(String.class).set("Hello").build()).build();
		HAssert.assertEquals("Hello", metadata.of(A.class).get(String.class));
		HAssert.assertException(NoAccessorFoundException.class, () -> metadata.of(B.class).isPresent(String.class));
	}

	@Test
	public void specFunctionalSubjectFunctionalSet() {
		final IMetadata metadata = Metadata.builder().mixins(mixins -> mixins.subject().testSubject(s -> {
			if (!(s instanceof IElementSubject)) return false;
			return A.class.isAssignableFrom((Class<?>) ((IElementSubject) s).getElement());
		}).test(p -> p.getObjectType().isAssignableFrom(ITypeRef.of(String.class))).set("Hello").build()).build();
		HAssert.assertEquals("Hello", metadata.of(A.class).get(String.class));
		HAssert.assertException(NoAccessorFoundException.class, () -> metadata.of(B.class).isPresent(String.class));
	}

	@Test
	public void specFunctionalTypeFunctionalSet() {
		final IMetadata metadata = Metadata.builder().mixins(mixins -> mixins.subject().testType(s -> (s instanceof IElementSubjectType) && Class.class.equals(((IElementSubjectType) s).getElement())).test(p -> p.getObjectType().isAssignableFrom(ITypeRef.of(String.class))).set("Hello").build()).build();
		HAssert.assertEquals("Hello", metadata.of(A.class).get(String.class));
		HAssert.assertEquals("Hello", metadata.of(B.class).get(String.class));
	}

	@Test
	public void specTypeTypeSet() {
		final IMetadata metadata = Metadata.builder().mixins(mixins -> mixins.subject().subject(Class.class, null).predicate(String.class).set("Hello").build()).build();
		HAssert.assertEquals("Hello", metadata.of(A.class).get(String.class));
		HAssert.assertEquals("Hello", metadata.of(B.class).get(String.class));
	}

	@Test
	public void unmixed() {
		HAssert.assertException(NoAccessorFoundException.class, () -> Metadata.getStandard().of(getClass()).bind(Element.class));
	}
}
