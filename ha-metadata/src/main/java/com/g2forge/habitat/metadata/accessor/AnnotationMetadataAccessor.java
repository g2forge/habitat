package com.g2forge.habitat.metadata.accessor;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.alexandria.java.core.marker.ISingleton;
import com.g2forge.habitat.metadata.annotations.IJavaAnnotations;
import com.g2forge.habitat.metadata.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.predicate.IPredicate;
import com.g2forge.habitat.metadata.predicate.IPredicateType;
import com.g2forge.habitat.metadata.subject.ISubject;

public class AnnotationMetadataAccessor implements IMetadataAccessor, ISingleton {
	protected static class AnnotationPredicate<T extends Annotation> implements IPredicate<T> {
		protected static void check(Class<?> type) {
			final Retention retention = type.getAnnotation(Retention.class);
			if ((retention == null) || !RetentionPolicy.RUNTIME.equals(retention.value())) throw new IllegalArgumentException("The annotation \"" + type.getName() + "\" cannot be read at runtime, since it is not retained!");
		}

		protected final ISubject subject;

		protected final IJavaAnnotations annotations;

		protected final AnnotationPredicateType<T> predicateType;

		public AnnotationPredicate(ISubject subject, IJavaAnnotations annotations, AnnotationPredicateType<T> predicateType) {
			this.subject = subject;
			this.annotations = annotations;
			this.predicateType = predicateType;

			check(getAnnotationType());
		}

		@Override
		public T get0() {
			return annotations.getAnnotation(getAnnotationType());
		}

		protected Class<T> getAnnotationType() {
			return predicateType.getObjectType().getErasedType();
		}

		@Override
		public ISubject getSubject() {
			return subject;
		}

		@Override
		public IPredicateType<T> getType() {
			return predicateType;
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