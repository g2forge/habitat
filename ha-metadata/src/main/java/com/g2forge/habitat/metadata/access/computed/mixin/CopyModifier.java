package com.g2forge.habitat.metadata.access.computed.mixin;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class CopyModifier implements ICopyModifier {
	protected final MixinMetadataRegistry.MixinMetadataRegistryBuilder builder;

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder merge(Collection<? extends ISubject> subjects) {
		throw new NotYetImplementedError();
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder of(AnnotatedElement element, Object value) {
		throw new NotYetImplementedError();
	}
}
