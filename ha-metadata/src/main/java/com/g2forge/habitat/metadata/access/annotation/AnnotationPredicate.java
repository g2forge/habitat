package com.g2forge.habitat.metadata.access.annotation;

import java.lang.annotation.Annotation;

import com.g2forge.habitat.metadata.annotations.ContainerAnnotationReflection;
import com.g2forge.habitat.metadata.annotations.IJavaAnnotated;
import com.g2forge.habitat.metadata.type.predicate.IAnnotationPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.IAnnotatedSubject;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
public class AnnotationPredicate<T extends Annotation> implements IPredicate<T> {
	protected final IAnnotatedSubject subject;

	protected final IAnnotationPredicateType<T> type;

	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = ContainerAnnotationReflection.createIfContainer(getType().getAnnotationType());

	@Override
	public T get0() {
		final Class<T> annotationType = getType().getAnnotationType();
		final IJavaAnnotated annotated = getSubject().getAnnotated();
		final T retVal = annotated.getAnnotation(annotationType);
		if (retVal != null) return retVal;

		final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = getContainerAnnotationReflection();
		if (containerAnnotationReflection == null) return null;
		final Annotation repeatable = annotated.getAnnotation(containerAnnotationReflection.getRepeatable());
		if (repeatable == null) return null;
		return containerAnnotationReflection.getCollectionStrategy().builder().add(repeatable).get();
	}

	@Override
	public boolean isPresent() {
		final IJavaAnnotated annotated = getSubject().getAnnotated();
		final boolean retVal = annotated.isAnnotated(getType().getAnnotationType());
		if (retVal) return true;

		final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = getContainerAnnotationReflection();
		if (containerAnnotationReflection == null) return false;
		return annotated.isAnnotated(containerAnnotationReflection.getRepeatable());
	}
}