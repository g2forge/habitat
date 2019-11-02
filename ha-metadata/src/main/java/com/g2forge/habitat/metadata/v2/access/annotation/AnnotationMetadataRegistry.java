package com.g2forge.habitat.metadata.v2.access.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.g2forge.habitat.metadata.v2.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.v2.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.v2.type.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.type.subject.ElementSubjectType;
import com.g2forge.habitat.metadata.v2.type.subject.ISubjectType;

import lombok.AccessLevel;
import lombok.Getter;

public class AnnotationMetadataRegistry implements IMetadataRegistry {
	protected static <T> void check(IPredicateType<?> predicateType) {
		if (!(predicateType instanceof AnnotationPredicateType)) throw new IllegalArgumentException(String.format("%1$s is not an annotation predicate type!", predicateType));
		final Class<? extends Annotation> type = ((AnnotationPredicateType<?>) predicateType).getAnnotationType();
		final Retention retention = type.getAnnotation(Retention.class);
		if ((retention == null) || !RetentionPolicy.RUNTIME.equals(retention.value())) throw new IllegalArgumentException("The annotation \"" + type.getName() + "\" cannot be read at runtime, since it is not retained!");
	}

	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	private final IMetadataAccessor accessor = new AnnotationMetadataAccessor();

	@Override
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) {
		if (context == null) throw new NullPointerException("The find context must be provided!");
		if (!(subjectType instanceof ElementSubjectType)) throw new IllegalArgumentException(String.format("%1$s is not an element subject type!", subjectType));
		check(predicateType);
		return getAccessor();
	}
}