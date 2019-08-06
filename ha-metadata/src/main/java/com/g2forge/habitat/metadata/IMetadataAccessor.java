package com.g2forge.habitat.metadata;

import com.g2forge.habitat.metadata.predicate.IPredicate;
import com.g2forge.habitat.metadata.predicate.IPredicateType;
import com.g2forge.habitat.metadata.subject.ISubject;

public interface IMetadataAccessor {
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType);

	public default <T> T get(ISubject subject, IPredicateType<T> predicateType) {
		return bind(subject, predicateType).get0();
	}
}
