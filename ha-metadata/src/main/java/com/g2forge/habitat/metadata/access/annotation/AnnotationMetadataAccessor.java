package com.g2forge.habitat.metadata.access.annotation;

import java.lang.annotation.Annotation;

import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.type.predicate.IAnnotationPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.IElementSubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class AnnotationMetadataAccessor<T extends Annotation> implements ITypedMetadataAccessor<T, IElementSubject, IAnnotationPredicateType<T>> {
	protected final AnnotationMetadataRegistry registry;

	@Override
	public IPredicate<T> bindTyped(IElementSubject subject, IAnnotationPredicateType<T> predicateType) {
		getRegistry().check(predicateType);
		return new AnnotationPredicate<>(subject, predicateType);
	}
}