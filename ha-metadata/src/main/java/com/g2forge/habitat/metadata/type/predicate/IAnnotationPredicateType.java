package com.g2forge.habitat.metadata.type.predicate;

import java.lang.annotation.Annotation;

public interface IAnnotationPredicateType<T extends Annotation> extends IPredicateType<T> {
	public Class<T> getAnnotationType();
}
