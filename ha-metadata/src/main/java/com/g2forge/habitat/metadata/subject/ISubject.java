package com.g2forge.habitat.metadata.subject;

import java.lang.annotation.Annotation;

import com.g2forge.alexandria.java.type.ITyped;
import com.g2forge.habitat.metadata.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.predicate.IPredicate;
import com.g2forge.habitat.metadata.predicate.IPredicateType;

public interface ISubject extends ITyped<ISubjectType> {
	public <T> IPredicate<T> bind(IPredicateType<T> predicateType);

	public default <T extends Annotation> T get(Class<T> type) {
		return get(new AnnotationPredicateType<>(type));
	}

	public default <T> T get(IPredicateType<T> predicateType) {
		return bind(predicateType).get0();
	}

	public default boolean isPresent(Class<? extends Annotation> type) {
		return isPresent(new AnnotationPredicateType<>(type));
	}

	public default boolean isPresent(IPredicateType<?> predicateType) {
		return bind(predicateType).isPresent();
	}
}
