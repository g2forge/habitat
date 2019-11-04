package com.g2forge.habitat.metadata.v2.access.annotation;

import java.lang.annotation.Annotation;

import com.g2forge.habitat.metadata.annotations.ContainerAnnotationReflection;
import com.g2forge.habitat.metadata.annotations.ElementJavaAnnotations;
import com.g2forge.habitat.metadata.v2.type.predicate.IAnnotationPredicateType;
import com.g2forge.habitat.metadata.v2.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.v2.value.subject.ElementSubject;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AnnotationPredicate<T extends Annotation> implements IPredicate<T> {
	protected final ElementSubject subject;

	protected final IAnnotationPredicateType<T> type;

	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	private final ElementJavaAnnotations annotations = new ElementJavaAnnotations(subject.getElement());

	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	private final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = ContainerAnnotationReflection.createIfContainer(getType().getAnnotationType());

	@Override
	public T get0() {
		final Class<T> annotationType = getType().getAnnotationType();
		final T retVal = getAnnotations().getAnnotation(annotationType);
		if (retVal != null) return retVal;

		final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = getContainerAnnotationReflection();
		if (containerAnnotationReflection == null) return null;
		final Annotation repeatable = getAnnotations().getAnnotation(containerAnnotationReflection.getRepeatable());
		if (repeatable == null) return null;
		return containerAnnotationReflection.wrap(repeatable);
	}

	@Override
	public boolean isPresent() {
		final boolean retVal = getAnnotations().isAnnotated(getType().getAnnotationType());
		if (retVal) return true;

		final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = getContainerAnnotationReflection();
		if (containerAnnotationReflection == null) return false;
		return getAnnotations().isAnnotated(containerAnnotationReflection.getRepeatable());
	}
}