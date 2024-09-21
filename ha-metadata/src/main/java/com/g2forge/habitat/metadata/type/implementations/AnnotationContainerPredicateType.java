package com.g2forge.habitat.metadata.type.implementations;

import java.lang.annotation.Annotation;

import com.g2forge.alexandria.collection.strategy.ICollectionStrategy;
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
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class AnnotationContainerPredicateType<T extends Annotation, U extends Annotation> implements IContainerPredicateType<T, U>, IAnnotationPredicateType<T> {
	@ToString.Exclude
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

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + getAnnotationType() + ")";
	}
}
