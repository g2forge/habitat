package com.g2forge.habitat.metadata.access.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.g2forge.habitat.metadata.access.AMetadataRegistry;
import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.type.predicate.IAnnotationPredicateType;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.IElementSubjectType;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public class AnnotationMetadataRegistry extends AMetadataRegistry<IElementSubjectType> {
	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final ITypedMetadataAccessor accessor = new AnnotationMetadataAccessor(this);

	@Override
	protected void check(IPredicateType<?> predicateType) {
		final Class<? extends Annotation> type = ((IAnnotationPredicateType<?>) predicateType).getAnnotationType();
		final Retention retention = type.getAnnotation(Retention.class);
		if ((retention == null) || !RetentionPolicy.RUNTIME.equals(retention.value())) throw new IllegalArgumentException("The annotation \"" + type.getName() + "\" cannot be read at runtime, since it is not retained!");
	}
}