package com.g2forge.habitat.metadata.access.computed;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.habitat.metadata.access.computed.MixinMetadataRegistry.IPredicateModifier;
import com.g2forge.habitat.metadata.access.computed.MixinMetadataRegistry.ISubjectModifier;
import com.g2forge.habitat.metadata.access.computed.MixinMetadataRegistry.MixinMetadataRegistryBuilder;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class SubjectModifier implements ISubjectModifier {
	protected final MixinMetadataRegistryBuilder builder;

	@Override
	public MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}

	@Override
	public IPredicateModifier merge(Collection<? extends ISubject> subjects) {
		throw new NotYetImplementedError();
	}

	@Override
	public IPredicateModifier of(AnnotatedElement element, Object value) {
		return new PredicateModifier(getBuilder());
	}
}