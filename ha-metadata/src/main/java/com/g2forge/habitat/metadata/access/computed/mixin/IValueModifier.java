package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.ISupplier;
import com.g2forge.alexandria.java.function.builder.IModifier;

public interface IValueModifier<T> extends IModifier<MixinMetadataRegistry.MixinMetadataRegistryBuilder> {
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder absent();

	public ICopyModifier copy();

	public MixinMetadataRegistry.MixinMetadataRegistryBuilder functional(ISupplier<? super T> supplier);

	public MixinMetadataRegistry.MixinMetadataRegistryBuilder set(T value);
}