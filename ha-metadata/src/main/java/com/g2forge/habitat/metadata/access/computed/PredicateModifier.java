package com.g2forge.habitat.metadata.access.computed;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class PredicateModifier implements MixinMetadataRegistry.IPredicateModifier {
	protected final MixinMetadataRegistry.MixinMetadataRegistryBuilder builder;

	@Override
	public <T> MixinMetadataRegistry.IValueModifier<T> bind(Class<T> type) {
		return new ValueModifier<>(getBuilder());
	}

	@Override
	public <T> MixinMetadataRegistry.IValueModifier<T> bind(IPredicateType<T> predicateType) {
		return new ValueModifier<>(getBuilder());
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}
}