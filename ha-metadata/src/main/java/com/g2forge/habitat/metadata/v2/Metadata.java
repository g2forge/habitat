package com.g2forge.habitat.metadata.v2;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.habitat.metadata.annotations.DynamicAnnotationInvocationHandler;
import com.g2forge.habitat.metadata.annotations.ElementJavaAnnotations;
import com.g2forge.habitat.metadata.v2.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.v2.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.v2.access.IMetadataRegistry.IFindContext;
import com.g2forge.habitat.metadata.v2.type.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.type.subject.ElementSubjectType;
import com.g2forge.habitat.metadata.v2.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.v2.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.v2.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.v2.value.subject.ElementSubject;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Metadata implements IMetadata {
	protected static class AnnotationMetadataRegistry implements IMetadataRegistry {
		protected static void check(Class<? extends Annotation> type) {
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

		@Override
		public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) {
			if (context == null) throw new NullPointerException("The find context must be provided!");
			if (!(subjectType instanceof ElementSubjectType)) throw new IllegalArgumentException(String.format("%1$s is not an element subject type!", subjectType));
			if (!(predicateType instanceof AnnotationPredicateType)) throw new IllegalArgumentException(String.format("%1$s is not an annotation predicate type!", predicateType));
			check(((AnnotationPredicateType<?>) ((AnnotationPredicateType<?>) predicateType)).getAnnotationType());

			return new IMetadataAccessor() {
				@Override
				public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType) {
					if (!(subject instanceof ElementSubject)) throw new IllegalArgumentException(String.format("%1$s is not an element subject!", subject));
					if (!(predicateType instanceof AnnotationPredicateType)) throw new IllegalArgumentException(String.format("%1$s is not an annotation predicate type!", predicateType));

					final ElementSubject castSubject = (ElementSubject) subject;
					return get((IPredicateType) predicateType, castSubject);
				}

				protected <T extends Annotation> IPredicate<T> get(IPredicateType<T> predicateType, final ElementSubject castSubject) {
					final AnnotationPredicateType<T> castPredicateType = (AnnotationPredicateType<T>) predicateType;
					final ElementJavaAnnotations annotations = new ElementJavaAnnotations(castSubject.getElement());
					return new IPredicate<T>() {
						@Override
						public T get0() {
							final Class<T> type = castPredicateType.getAnnotationType();
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

						@Override
						public ISubject getSubject() {
							return castSubject;
						}

						@Override
						public IPredicateType<T> getType() {
							return predicateType;
						}

						@Override
						public boolean isPresent() {
							return annotations.isAnnotated(castPredicateType.getAnnotationType());
						}
					};
				}
			};
		}
	}

	@RequiredArgsConstructor
	@Getter(AccessLevel.PROTECTED)
	protected static class MetadataValueContext implements IMetadataValueContext {
		protected final IMetadataRegistry registry;

		@Override
		public IMetadataAccessor find(ISubjectType subjectType, IPredicateType<?> predicateType) {
			return getRegistry().find(new IFindContext() {}, subjectType, predicateType);
		}
	}

	@Getter(lazy = true)
	private static final IMetadata standard = new Metadata();

	@Getter(lazy = true)
	private final IMetadataValueContext context = new MetadataValueContext(new AnnotationMetadataRegistry());

	@Override
	public ISubject of(AnnotatedElement element, Object value) {
		if ((element == null) && (value instanceof AnnotatedElement)) return of((AnnotatedElement) value, null);
		if ((value != null) || (element == null)) throw new NotYetImplementedError();

		return new ElementSubject(getContext(), element);
	}
}