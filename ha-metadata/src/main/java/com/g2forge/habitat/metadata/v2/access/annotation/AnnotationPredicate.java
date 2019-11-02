package com.g2forge.habitat.metadata.v2.access.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

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
@RequiredArgsConstructor
class AnnotationPredicate<T extends Annotation> implements IPredicate<T> {
	/**
	 * Get the repeatable annotation that <code>container</code> contains, or return <code>null</code> if <code>container</code> isn't a repeatable annotation
	 * container.
	 * 
	 * @param container The type of an annotation which might be a repeatable container.
	 * @return The type of the repeatable annotation, or <code>null</code> if <code>container</code> isn't actually a container.
	 */
	protected static Class<? extends Annotation> getRepeatable(Class<? extends Annotation> container) {
		final Method[] methods = container.getDeclaredMethods();
		if ((methods.length == 1) && (methods[0].getName().equals("value"))) {
			final Class<?> returnType = methods[0].getReturnType();
			if (returnType.isArray()) {
				final Class<?> componentType = returnType.getComponentType();
				if (componentType.isAnnotation()) {
					final Repeatable repeatable = componentType.getAnnotation(Repeatable.class);
					if (repeatable.value().equals(container)) {
						@SuppressWarnings({ "rawtypes", "unchecked" })
						final Class<? extends Annotation> retVal = (Class) componentType;
						return retVal;
					}
				}
			}
		}
		return null;
	}

	protected final ElementSubject subject;

	protected final AnnotationPredicateType<T> type;

	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	private final ElementJavaAnnotations annotations = new ElementJavaAnnotations(subject.getElement());

	@Override
	public T get0() {
		final Class<T> annotationType = getType().getAnnotationType();
		final T retVal = getAnnotations().getAnnotation(annotationType);
		if (retVal == null) {
			final Class<? extends Annotation> repeatableType = AnnotationPredicate.getRepeatable(annotationType);
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