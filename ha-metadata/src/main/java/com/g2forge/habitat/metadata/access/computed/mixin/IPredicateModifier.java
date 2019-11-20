package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.IPredicate1;
import com.g2forge.alexandria.java.function.builder.IModifier;
import com.g2forge.habitat.metadata.type.IMetadataPredicateTypeFactory;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.IMetadataPredicateFactory;

public interface IPredicateModifier extends IModifier<MixinMetadataRegistry.MixinMetadataRegistryBuilder>, IMetadataPredicateFactory<IValueModifier<?>>, IMetadataPredicateTypeFactory<IValueModifier<?>> {
	@Override
	public <T> IValueModifier<T> bind(Class<T> type);

	@Override
	public <T> IValueModifier<T> bind(IPredicateType<T> predicateType);

	@Override
	public <T> IValueModifier<T> predicate(Class<T> type);

	public <T> IValueModifier<T> test(IPredicate1<? super IPredicateType<?>> filter);
}