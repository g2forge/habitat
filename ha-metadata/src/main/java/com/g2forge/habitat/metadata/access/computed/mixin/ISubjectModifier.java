package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.IPredicate1;
import com.g2forge.alexandria.java.function.builder.IModifier;
import com.g2forge.habitat.metadata.type.IMetadataSubjectTypeFactory;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataSubjectFactory;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface ISubjectModifier extends IModifier<MixinMetadataRegistry.MixinMetadataRegistryBuilder>, IMetadataSubjectFactory<IPredicateModifier>, IMetadataSubjectTypeFactory<IPredicateModifier> {
	public IPredicateModifier testSubject(IPredicate1<? super ISubject> filter);

	public IPredicateModifier testType(IPredicate1<? super ISubjectType> filter);
}