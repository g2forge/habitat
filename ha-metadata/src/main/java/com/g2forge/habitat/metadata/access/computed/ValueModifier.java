package com.g2forge.habitat.metadata.access.computed;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.alexandria.java.function.ISupplier;
import com.g2forge.habitat.metadata.access.computed.MixinMetadataRegistry.IValueModifier;
import com.g2forge.habitat.metadata.access.computed.MixinMetadataRegistry.MixinMetadataRegistryBuilder;

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
	public MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}

	@Override
	public MixinMetadataRegistryBuilder functional(ISupplier<? super T> supplier) {
		throw new NotYetImplementedError();
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder set(T value) {
		throw new NotYetImplementedError();
	}
}
