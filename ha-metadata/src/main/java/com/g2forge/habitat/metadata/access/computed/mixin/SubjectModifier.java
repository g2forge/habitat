package com.g2forge.habitat.metadata.access.computed.mixin;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class SubjectModifier implements ISubjectModifier {
	protected final MixinMetadataRegistry.MixinMetadataRegistryBuilder builder;

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}

	@Override
	public IPredicateModifier merge(Collection<? extends ISubject> subjects) {
		return new PredicateModifier(getBuilder());
	}

	@Override
	public IPredicateModifier of(AnnotatedElement element, Object value) {
		return new PredicateModifier(getBuilder());
	}

	@Override
	public IPredicateModifier subject(Class<? extends AnnotatedElement> elementType, Class<?> valueType) {
		return new PredicateModifier(getBuilder());
	}

	@Override
	public IPredicateModifier subject(Collection<? extends IPredicateModifier> types) {
		return new PredicateModifier(getBuilder());
	}
}