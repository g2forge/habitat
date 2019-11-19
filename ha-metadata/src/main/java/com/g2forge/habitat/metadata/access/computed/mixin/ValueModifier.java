package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.ISupplier;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class ValueModifier<T> implements IValueModifier<T> {
	protected final MixinMetadataRegistry.MixinMetadataRegistryBuilder builder;

	protected final MixinMetadataAccessor.MixinMetadataAccessorBuilder accessor;

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder absent() {
		return getBuilder().applicable(getAccessor().supplier(null).build());
	}

	@Override
	public ICopyModifier copy() {
		return new CopyModifier(getBuilder());
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder set(T value) {
		return getBuilder().applicable(getAccessor().supplier(ISupplier.create(value)).build());
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder supply(ISupplier<? super T> supplier) {
		return getBuilder().applicable(getAccessor().supplier(supplier).build());
	}
}
