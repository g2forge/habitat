package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.builder.IModifier;
import com.g2forge.habitat.metadata.type.IMetadataSubjectTypeFactory;
import com.g2forge.habitat.metadata.value.IMetadataSubjectFactory;

public interface ISubjectModifier extends IModifier<MixinMetadataRegistry.MixinMetadataRegistryBuilder>, IMetadataSubjectFactory<IPredicateModifier>, IMetadataSubjectTypeFactory<IPredicateModifier> {}