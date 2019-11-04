package com.g2forge.habitat.metadata.v2.access.annotation;

import com.g2forge.habitat.metadata.v2.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.v2.type.predicate.IAnnotationPredicateType;
import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.v2.value.subject.ElementSubject;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class AnnotationMetadataAccessor implements IMetadataAccessor {
	protected final AnnotationMetadataRegistry registry;

	@Override
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType) {
		if (!(subject instanceof ElementSubject)) throw new IllegalArgumentException(String.format("%1$s is not an element subject!", subject));
		getRegistry().check(predicateType);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final IPredicate<T> retVal = new AnnotationPredicate((ElementSubject) subject, (IAnnotationPredicateType) predicateType);
		return retVal;
	}
}