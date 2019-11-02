package com.g2forge.habitat.metadata.v2.access.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;

import com.g2forge.habitat.metadata.annotations.DynamicAnnotationInvocationHandler;
import com.g2forge.habitat.metadata.annotations.ElementJavaAnnotations;
import com.g2forge.habitat.metadata.v2.type.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.v2.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.v2.value.subject.ElementSubject;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor class AnnotationPredicate<T extends Annotation> implements IPredicate<T> {
	protected final ElementSubject subject;

	protected final AnnotationPredicateType<T> type;

	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	private final ElementJavaAnnotations annotations = new ElementJavaAnnotations(subject.getElement());

	@Override
	public T get0() {
		final Class<T> annotationType = getType().getAnnotationType();
		final T retVal = getAnnotations().getAnnotation(annotationType);
		if (retVal == null) {
			final Class<? extends Annotation> repeatableType = AnnotationMetadataAccessor.getRepeatable(annotationType);
			if (repeatableType != null) {
				final Object repeatable = getAnnotations().getAnnotation(repeatableType);
				if (repeatable == null) return null;

				final Object array = Array.newInstance(repeatableType, 1);
				Array.set(array, 0, repeatable);

				@SuppressWarnings({ "rawtypes", "unchecked" })
				final T retValDynamic = (T) new DynamicAnnotationInvocationHandler.Builder<Annotation>((Class) getType().getAnnotationType()).add("value", array).build();
				return retValDynamic;
			}
		}
		return retVal;
	}

	@Override
	public boolean isPresent() {
		return getAnnotations().isAnnotated(getType().getAnnotationType());
	}
}