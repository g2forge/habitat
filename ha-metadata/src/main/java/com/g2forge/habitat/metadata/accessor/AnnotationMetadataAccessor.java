package com.g2forge.habitat.metadata.accessor;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.alexandria.java.core.marker.ISingleton;
import com.g2forge.habitat.metadata.annotations.DynamicAnnotationInvocationHandler;
import com.g2forge.habitat.metadata.annotations.IJavaAnnotations;
import com.g2forge.habitat.metadata.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.predicate.IPredicate;
import com.g2forge.habitat.metadata.predicate.IPredicateType;
import com.g2forge.habitat.metadata.subject.ISubject;

import lombok.Getter;

public class AnnotationMetadataAccessor implements IMetadataAccessor, ISingleton {
	protected static class AnnotationPredicate<T extends Annotation> implements IPredicate<T> {
		protected static void check(Class<?> type) {
			final Retention retention = type.getAnnotation(Retention.class);
			if ((retention == null) || !RetentionPolicy.RUNTIME.equals(retention.value())) throw new IllegalArgumentException("The annotation \"" + type.getName() + "\" cannot be read at runtime, since it is not retained!");
		}

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

		@Getter
		protected final ISubject subject;

		protected final IJavaAnnotations annotations;

		@Getter
		protected final AnnotationPredicateType<T> type;

		public AnnotationPredicate(ISubject subject, IJavaAnnotations annotations, AnnotationPredicateType<T> type) {
			this.subject = subject;
			this.annotations = annotations;
			this.type = type;

			check(getAnnotationType());
		}

		@Override
		public T get0() {
			final Class<T> type = getAnnotationType();
			final T retVal = annotations.getAnnotation(type);
			if (retVal == null) {
				final Class<? extends Annotation> repeatableType = getRepeatable(type);
				if (repeatableType != null) {
					final Object repeatable = annotations.getAnnotation(repeatableType);
					if (repeatable == null) return null;

					final Object array = Array.newInstance(repeatableType, 1);
					Array.set(array, 0, repeatable);

					@SuppressWarnings({ "rawtypes", "unchecked" })
					final T retValDynamic = (T) new DynamicAnnotationInvocationHandler.Builder<Annotation>((Class) type).add("value", array).build();
					return retValDynamic;
				}
			}
			return retVal;
		}

		protected Class<T> getAnnotationType() {
			return type.getObjectType().getErasedType();
		}

		@Override
		public boolean isPresent() {
			return annotations.isAnnotated(getAnnotationType());
		}
	}

	protected static final IMetadataAccessor instance = new AnnotationMetadataAccessor();

	public static IMetadataAccessor create() {
		return instance;
	}

	protected AnnotationMetadataAccessor() {}

	protected <T extends Annotation> IPredicate<T> bind(ISubject subject, IJavaAnnotations annotations, AnnotationPredicateType<T> predicateType) {
		return new AnnotationPredicate<T>(subject, annotations, predicateType);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> IPredicate<T> bind(ISubject subject, IJavaAnnotations annotations, IPredicateType<T> predicateType) {
		if (predicateType instanceof AnnotationPredicateType) return bind(subject, annotations, (AnnotationPredicateType) predicateType);
		throw new NotYetImplementedError(predicateType + " is not yet implemented!");
	}
}