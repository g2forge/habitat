package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.builder.IModifier;
import com.g2forge.habitat.metadata.value.IMetadataSubjectFactory;

public interface ICopyModifier extends IModifier<MixinMetadataRegistry.MixinMetadataRegistryBuilder>, IMetadataSubjectFactory<MixinMetadataRegistry.MixinMetadataRegistryBuilder> {}