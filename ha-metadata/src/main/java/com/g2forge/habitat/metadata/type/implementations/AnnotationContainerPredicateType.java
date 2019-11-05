package com.g2forge.habitat.metadata.type.implementations;

import java.lang.annotation.Annotation;

import com.g2forge.alexandria.adt.collection.strategy.ICollectionStrategy;
import com.g2forge.habitat.metadata.annotations.ContainerAnnotationReflection;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IAnnotationPredicateType;
import com.g2forge.habitat.metadata.type.predicate.IContainerPredicateType;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class AnnotationContainerPredicateType<T extends Annotation, U extends Annotation> implements IContainerPredicateType<T, U>, IAnnotationPredicateType<T> {
	protected final IMetadataTypeContext context;

	@Getter(AccessLevel.PROTECTED)
	protected final ContainerAnnotationReflection<T, U> containerAnnotationReflection;

	protected final IPredicateType<U> repeatable;

	@Override
	public Class<T> getAnnotationType() {
		return getContainerAnnotationReflection().getContainer();
	}

	@Override
	public ICollectionStrategy<T, U> getCollectionStrategy() {
		return getContainerAnnotationReflection().getCollectionStrategy();
	}
}
