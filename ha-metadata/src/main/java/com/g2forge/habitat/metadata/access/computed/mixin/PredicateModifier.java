package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class PredicateModifier implements IPredicateModifier {
	protected final MixinMetadataRegistry.MixinMetadataRegistryBuilder builder;

	@Override
	public <T> IValueModifier<T> bind(Class<T> type) {
		return new ValueModifier<>(getBuilder());
	}

	@Override
	public <T> IValueModifier<T> bind(IPredicateType<T> predicateType) {
		return new ValueModifier<>(getBuilder());
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}

	@Override
	public <T> IValueModifier<T> predicate(Class<T> type) {
		return new ValueModifier<>(getBuilder());
	}
}