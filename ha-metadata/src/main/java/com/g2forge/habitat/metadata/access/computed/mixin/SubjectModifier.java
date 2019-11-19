package com.g2forge.habitat.metadata.access.computed.mixin;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.alexandria.java.function.IPredicate1;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
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
		return testSubject(null);
	}

	@Override
	public IPredicateModifier of(AnnotatedElement element, Object value) {
		// this.subject(element.getClass(), value.getClass());
		return testSubject(null);
	}

	@Override
	public IPredicateModifier subject(Class<? extends AnnotatedElement> elementType, Class<?> valueType) {
		return testType(null);
	}

	@Override
	public IPredicateModifier subject(Collection<? extends IPredicateModifier> types) {
		return testType(null);
	}

	@Override
	public IPredicateModifier testSubject(IPredicate1<? super ISubject> filter) {
		return new PredicateModifier(getBuilder(), MixinMetadataAccessor.builder().subject(filter));
	}

	@Override
	public IPredicateModifier testType(IPredicate1<? super ISubjectType> filter) {
		return new PredicateModifier(getBuilder(), MixinMetadataAccessor.builder().subjectType(filter).subject(s -> filter.test(s.getType())));
	}
}