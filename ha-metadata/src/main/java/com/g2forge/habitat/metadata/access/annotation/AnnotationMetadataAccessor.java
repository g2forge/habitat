package com.g2forge.habitat.metadata.access.annotation;

import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.type.predicate.IAnnotationPredicateType;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.IAnnotatedSubject;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class AnnotationMetadataAccessor implements IMetadataAccessor {
	protected final AnnotationMetadataRegistry registry;

	@Override
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType) {
		if (!(subject instanceof IAnnotatedSubject)) throw new IllegalArgumentException(String.format("%1$s is not an annotated subject!", subject));
		getRegistry().check(predicateType);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final IPredicate<T> retVal = new AnnotationPredicate((IAnnotatedSubject) subject, (IAnnotationPredicateType) predicateType);
		return retVal;
	}
}