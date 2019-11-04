package com.g2forge.habitat.metadata.value.subject;

import java.lang.annotation.Annotation;

import com.g2forge.alexandria.java.type.ITyped;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValue;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;

public interface ISubject extends ITyped<ISubjectType>, IMetadataValue {
	public default <T> IPredicate<T> bind(IPredicateType<T> predicateType) {
		return getContext().find(getType(), predicateType).bind(this, predicateType);
	}
	
	public default <T extends Annotation> IPredicate<T> bind(Class<T> type) {
		final IPredicateType<T> predicateType = getContext().getTypeContext().predicate(type);
		return bind(predicateType);
	}

	public default <T extends Annotation> T get(Class<T> type) {
		final IPredicateType<T> predicateType = getContext().getTypeContext().predicate(type);
		return get(predicateType);
	}

	public default <T> T get(IPredicateType<T> predicateType) {
		return bind(predicateType).get0();
	}

	public default boolean isPresent(Class<? extends Annotation> type) {
		final IPredicateType<? extends Annotation> predicateType = getContext().getTypeContext().predicate(type);
		return isPresent(predicateType);
	}

	public default boolean isPresent(IPredicateType<?> predicateType) {
		return bind(predicateType).isPresent();
	}
}
