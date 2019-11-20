package com.g2forge.habitat.metadata.access.computed.mixin;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class CopyModifier implements ICopyModifier {
	protected final MixinMetadataRegistry.MixinMetadataRegistryBuilder builder;

	protected final MixinMetadata.MixinMetadataBuilder metadata;

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder of(AnnotatedElement element, Object value) {
		return getBuilder().accessor(getMetadata().accessor(new IMetadataAccessor() {
			@Override
			public <_T> IPredicate<_T> bind(ISubject subject, IPredicateType<_T> predicateType) {
				return subject.getContext().of(element, value).bind(predicateType);
			}
		}).build());
	}
}
