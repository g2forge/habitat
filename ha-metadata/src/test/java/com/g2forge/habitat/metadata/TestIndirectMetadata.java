package com.g2forge.habitat.metadata;

import org.junit.Test;

import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.access.indirect.IndirectMetadata;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.ConstantPredicate;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.IElementSubject;
import com.g2forge.habitat.metadata.value.subject.IValueSubject;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public class TestIndirectMetadata {
	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	@IndirectMetadata(ElementMetadataAccessor.class)
	public static class Element {
		protected final Class<?> type;
	}

	public static class ElementMetadataAccessor implements ITypedMetadataAccessor<Element, IElementSubject, IPredicateType<Element>> {
		@Override
		public IPredicate<Element> bindTyped(IElementSubject subject, IPredicateType<Element> predicateType) {
			return ConstantPredicate.present(subject, predicateType, new Element((Class<?>) subject.getElement()));
		}
	}

	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	@IndirectMetadata(InstanceOfMetadataAccessor.class)
	public static class InstanceOf {
		protected final boolean assignable;
	}

	public static class InstanceOfMetadataAccessor implements ITypedMetadataAccessor<InstanceOf, IValueSubject, IPredicateType<InstanceOf>> {
		@Override
		public IPredicate<InstanceOf> bindTyped(IValueSubject subject, IPredicateType<InstanceOf> predicateType) {
			return ConstantPredicate.present(subject, predicateType, new InstanceOf(((Class<?>) subject.getElement()).isInstance(subject.getValue())));
		}
	}

	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	@IndirectMetadata(ValueMetadataAccessor.class)
	public static class Value {
		protected final Object value;
	}

	public static class ValueMetadataAccessor implements ITypedMetadataAccessor<Value, IValueSubject, IPredicateType<Value>> {
		@Override
		public IPredicate<Value> bindTyped(IValueSubject subject, IPredicateType<Value> predicateType) {
			return ConstantPredicate.present(subject, predicateType, new Value(subject.getValue()));
		}
	}

	@Test
	public void element() {
		final IPredicate<Element> predicate = Metadata.getStandard().of(getClass()).bind(Element.class);
		HAssert.assertEquals(getClass(), predicate.get0().getType());
	}

	@Test
	public void instanceOf() {
		final IMetadata metadata = Metadata.getStandard();
		HAssert.assertTrue(metadata.of(getClass(), this).get(InstanceOf.class).isAssignable());
		HAssert.assertFalse(metadata.of(getClass(), new Object()).get(InstanceOf.class).isAssignable());
		HAssert.assertFalse(metadata.of(Comparable.class, new Object()).get(InstanceOf.class).isAssignable());
		HAssert.assertTrue(metadata.of(Object.class, this).get(InstanceOf.class).isAssignable());
	}

	@Test
	public void value() {
		final IPredicate<Value> predicate = Metadata.getStandard().of(getClass(), this).bind(Value.class);
		HAssert.assertSame(this, predicate.get0().getValue());
	}
}
