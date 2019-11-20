package com.g2forge.habitat.metadata.access.computed.mixin;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.alexandria.java.function.IPredicate1;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.type.subject.SubjectTypeDescriptor;
import com.g2forge.habitat.metadata.value.subject.ISubject;
import com.g2forge.habitat.metadata.value.subject.SubjectDescriptor;

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
	public IPredicateModifier of(AnnotatedElement element, Object value) {
		return testSubject(new SubjectDescriptor(element, value).reduce()::isMatch);
	}

	@Override
	public IPredicateModifier subject(Class<? extends AnnotatedElement> elementType, Class<?> valueType) {
		return testType(new SubjectTypeDescriptor(elementType, valueType).reduce()::isMatch);
	}

	@Override
	public IPredicateModifier testSubject(IPredicate1<? super ISubject> filter) {
		return new PredicateModifier(getBuilder(), MixinMetadataAccessor.builder().subject(filter.ignore1()));
	}

	@Override
	public IPredicateModifier testType(IPredicate1<? super ISubjectType> filter) {
		return new PredicateModifier(getBuilder(), MixinMetadataAccessor.builder().subjectType(filter.ignore1()).subject((s, c) -> filter.test(s.getType())));
	}
}