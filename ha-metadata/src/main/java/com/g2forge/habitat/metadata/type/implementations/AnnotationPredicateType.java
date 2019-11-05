package com.g2forge.habitat.metadata.type.implementations;

import java.lang.annotation.Annotation;

import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IAnnotationPredicateType;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class AnnotationPredicateType<T extends Annotation> implements IPredicateType<T>, IAnnotationPredicateType<T> {
	protected final IMetadataTypeContext context;

	protected final Class<T> annotationType;
}
