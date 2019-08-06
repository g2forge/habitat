package com.g2forge.habitat.metadata;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.accessor.IMetadataAccessor;
import com.g2forge.habitat.metadata.annotations.IJavaAnnotations;
import com.g2forge.habitat.metadata.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.predicate.IPredicate;
import com.g2forge.habitat.metadata.predicate.IPredicateType;
import com.g2forge.habitat.metadata.subject.ISubject;
import com.g2forge.habitat.trace.HTrace;

public class TestMetadata {
	@Retention(RetentionPolicy.RUNTIME)
	public @interface MyAnnotation {
		public String value();
	}

	@MyAnnotation("expected")
	@Test
	public void test() {
		final IMetadataContext metadata = new MetadataContext(new IMetadataAccessor() {
			protected <T extends Annotation> IPredicate<T> bind(ISubject subject, IJavaAnnotations annotations, AnnotationPredicateType<T> predicateType) {
				return new IPredicate<T>() {
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
				};
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public <T> IPredicate<T> bind(ISubject subject, IJavaAnnotations annotations, IPredicateType<T> predicateType) {
				if (predicateType instanceof AnnotationPredicateType) return bind(subject, annotations, (AnnotationPredicateType) predicateType);
				throw new NotYetImplementedError(predicateType + " is not yet implemented!");
			}
		});
		HAssert.assertEquals("expected", metadata.of(HTrace.getCaller()).get(MyAnnotation.class).value());
	}
}
