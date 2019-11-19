package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.alexandria.java.function.ISupplier;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class ValueModifier<T> implements IValueModifier<T> {
	protected final MixinMetadataRegistry.MixinMetadataRegistryBuilder builder;

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder absent() {
		throw new NotYetImplementedError();
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
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder functional(ISupplier<? super T> supplier) {
		throw new NotYetImplementedError();
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder set(T value) {
		throw new NotYetImplementedError();
	}
}
