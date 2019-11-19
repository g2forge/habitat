package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.builder.IModifier;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.IMetadataPredicateFactory;

public interface IPredicateModifier extends IModifier<MixinMetadataRegistry.MixinMetadataRegistryBuilder>, IMetadataPredicateFactory<IValueModifier<?>> {
	@Override
	public <T> IValueModifier<T> bind(Class<T> type);

	@Override
	public <T> IValueModifier<T> bind(IPredicateType<T> predicateType);
}