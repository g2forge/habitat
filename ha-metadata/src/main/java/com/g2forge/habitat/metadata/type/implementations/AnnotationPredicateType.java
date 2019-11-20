package com.g2forge.habitat.metadata.type.implementations;

import java.lang.annotation.Annotation;

import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IAnnotationPredicateType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class AnnotationPredicateType<T extends Annotation> implements IAnnotationPredicateType<T> {
	@ToString.Exclude
	protected final IMetadataTypeContext context;

	protected final Class<T> annotationType;

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + getAnnotationType() + ")";
	}
}
