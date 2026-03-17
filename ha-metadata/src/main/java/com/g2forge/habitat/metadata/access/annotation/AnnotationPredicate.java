package com.g2forge.habitat.metadata.access.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.java.core.helpers.HTree;
import com.g2forge.alexandria.java.reflect.annotations.IJavaAnnotations;
import com.g2forge.habitat.metadata.annotations.ContainerAnnotationReflection;
import com.g2forge.habitat.metadata.type.predicate.IAnnotationPredicateType;
import com.g2forge.habitat.metadata.value.predicate.APredicate;
import com.g2forge.habitat.metadata.value.subject.IElementSubject;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class AnnotationPredicate<T extends Annotation> extends APredicate<T> {
	protected final IElementSubject subject;

	protected final IAnnotationPredicateType<T> type;

	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = ContainerAnnotationReflection.createIfContainer(getType().getAnnotationType());

	@Override
	public T get() {
		final Class<T> annotationType = getType().getAnnotationType();
		if (annotationType.isAnnotationPresent(Inherited.class)) {
			// Inherited annotations
			final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = getContainerAnnotationReflection();
			if (containerAnnotationReflection == null) {
				final Optional<IElementSubject> subject = HTree.find(getSubject(), IElementSubject::getParents, s -> s.getAnnotations().isAnnotated(annotationType));
				if (!subject.isPresent()) return null;
				final IJavaAnnotations annotations = subject.get().getAnnotations();
				return annotations.getAnnotation(annotationType);
			} else {
				final Class<Annotation> repeatableAnnotationType = containerAnnotationReflection.getRepeatable();
				final List<Annotation> repeatableAnnotationValues = HTree.dfs(getSubject(), IElementSubject::getParents, false).flatMap(s -> {
					final IJavaAnnotations annotations = s.getAnnotations();
					final List<Annotation> retVal = new ArrayList<>();

					final T annotation = annotations.getAnnotation(annotationType);
					if (annotation != null) retVal.addAll(HCollection.asListIterable(containerAnnotationReflection.getCollectionStrategy().iterable(annotation)));

					final Annotation repeatable = annotations.getAnnotation(repeatableAnnotationType);
					if (repeatable != null) retVal.add(repeatable);

					return retVal.stream();
				}).collect(Collectors.toList());
				return containerAnnotationReflection.getCollectionStrategy().builder().add(repeatableAnnotationValues).get();
			}
		} else {
			// Non-inherited annotations present on the subject
			final IJavaAnnotations annotations = getSubject().getAnnotations();
			final T retVal = annotations.getAnnotation(annotationType);
			if (retVal != null) return retVal;

			// Non-inherited annotations which are repeated, and whose repeatable child is on the subject
			final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = getContainerAnnotationReflection();
			if (containerAnnotationReflection == null) return null;
			final Annotation repeatable = annotations.getAnnotation(containerAnnotationReflection.getRepeatable());
			if (repeatable == null) return null;
			return containerAnnotationReflection.getCollectionStrategy().builder().add(repeatable).get();
		}
	}

	@Override
	public boolean isPresent() {
		final Class<T> annotationType = getType().getAnnotationType();
		if (annotationType.isAnnotationPresent(Inherited.class)) {
			// Inherited annotations
			final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = getContainerAnnotationReflection();
			if (containerAnnotationReflection == null) return HTree.find(getSubject(), IElementSubject::getParents, s -> s.getAnnotations().isAnnotated(annotationType)).isPresent();
			else {
				final Class<Annotation> repeatableAnnotationType = containerAnnotationReflection.getRepeatable();
				return HTree.find(getSubject(), IElementSubject::getParents, s -> {
					final IJavaAnnotations annotations = s.getAnnotations();
					return annotations.isAnnotated(annotationType) || annotations.isAnnotated(repeatableAnnotationType);
				}).isPresent();
			}
		} else {
			final IJavaAnnotations annotations = getSubject().getAnnotations();
			final boolean retVal = annotations.isAnnotated(annotationType);
			if (retVal) return true;

			final ContainerAnnotationReflection<T, Annotation> containerAnnotationReflection = getContainerAnnotationReflection();
			if (containerAnnotationReflection == null) return false;
			return annotations.isAnnotated(containerAnnotationReflection.getRepeatable());
		}
	}
}