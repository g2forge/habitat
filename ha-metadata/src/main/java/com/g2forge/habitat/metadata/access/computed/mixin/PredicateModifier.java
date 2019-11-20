package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.IPredicate1;
import com.g2forge.alexandria.java.function.IPredicate2;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class PredicateModifier implements IPredicateModifier {
	protected final MixinMetadataRegistry.MixinMetadataRegistryBuilder builder;

	protected final MixinMetadataAccessor.MixinMetadataAccessorBuilder accessor;

	@Override
	public <T> IValueModifier<T> bind(Class<T> type) {
		return internal((p, c) -> c.predicate(type).equals(p));
	}

	@Override
	public <T> IValueModifier<T> bind(IPredicateType<T> predicateType) {
		return internal(IPredicate1.<IPredicateType<?>>isEqual(predicateType).ignore1());
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}

	protected <T> IValueModifier<T> internal(final IPredicate2<? super IPredicateType<?>, ? super TestContext> filter) {
		return new ValueModifier<>(getBuilder(), getAccessor().predicateType(filter));
	}

	@Override
	public <T> IValueModifier<T> predicate(Class<T> type) {
		return internal((p, c) -> c.predicate(type).equals(p));
	}

	@Override
	public <T> IValueModifier<T> test(IPredicate1<? super IPredicateType<?>> filter) {
		return internal(filter.ignore1());
	}
}