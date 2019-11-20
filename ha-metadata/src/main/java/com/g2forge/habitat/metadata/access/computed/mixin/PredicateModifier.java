package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.IPredicate1;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.predicate.PredicateTypeDescriptor;

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
		return test(new PredicateTypeDescriptor(type).reduce()::isMatch);
	}

	@Override
	public <T> IValueModifier<T> bind(IPredicateType<T> predicateType) {
		return test(new PredicateTypeDescriptor(predicateType.getPredicateID()).reduce()::isMatch);
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}

	@Override
	public <T> IValueModifier<T> predicate(Class<T> type) {
		return bind(type);
	}

	@Override
	public <T> IValueModifier<T> test(IPredicate1<? super IPredicateType<?>> filter) {
		return new ValueModifier<>(getBuilder(), getAccessor().predicateType(filter.ignore1()));
	}
}