package com.g2forge.habitat.metadata.v2.type.predicate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.List;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.java.type.ref.ITypeRef;
import com.g2forge.habitat.metadata.annotations.ContainerAnnotationReflection;
import com.g2forge.habitat.metadata.v2.type.IMetadataTypeContext;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class AnnotationContainerPredicateType<T extends Annotation, U extends Annotation> implements IContainerPredicateType<T, U>, IAnnotationPredicateType<T> {
	protected final IMetadataTypeContext context;

	@Getter(AccessLevel.PROTECTED)
	protected final ContainerAnnotationReflection<T, U> containerAnnotationReflection;

	protected final IPredicateType<U> repeatable;

	public Class<T> getAnnotationType() {
		return getContainerAnnotationReflection().getContainer();
	}

	@Override
	public ITypeRef<T> getObjectType() {
		return ITypeRef.of(getAnnotationType());
	}

	@Override
	public Class<?> getPredicateID() {
		return getAnnotationType();
	}

	@Override
	public List<U> unwrap(T value) {
		return HCollection.asList(getContainerAnnotationReflection().unwrap(value));
	}

	@Override
	public T wrap(List<U> list) {
		@SuppressWarnings("unchecked")
		final U[] array = (U[]) Array.newInstance(getContainerAnnotationReflection().getRepeatable(), list.size());
		return containerAnnotationReflection.wrap(list.toArray(array));
	}
}
