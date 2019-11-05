package com.g2forge.habitat.metadata.access.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.g2forge.habitat.metadata.access.AMetadataRegistry;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.predicate.IAnnotationPredicateType;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ElementSubjectType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

import lombok.AccessLevel;
import lombok.Getter;

public class AnnotationMetadataRegistry extends AMetadataRegistry {
	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	private final IMetadataAccessor accessor = new AnnotationMetadataAccessor(this);

	@Override
	protected void check(IPredicateType<?> predicateType) {
		if (!(predicateType instanceof IAnnotationPredicateType)) throw new NoAccessorFoundException(String.format("%1$s is not an annotation predicate type!", predicateType));
		final Class<? extends Annotation> type = ((IAnnotationPredicateType<?>) predicateType).getAnnotationType();
		final Retention retention = type.getAnnotation(Retention.class);
		if ((retention == null) || !RetentionPolicy.RUNTIME.equals(retention.value())) throw new IllegalArgumentException("The annotation \"" + type.getName() + "\" cannot be read at runtime, since it is not retained!");
	}

	@Override
	protected void check(ISubjectType subjectType) {
		if (!(subjectType instanceof ElementSubjectType)) throw new NoAccessorFoundException(String.format("%1$s is not an element subject type!", subjectType));
	}
}