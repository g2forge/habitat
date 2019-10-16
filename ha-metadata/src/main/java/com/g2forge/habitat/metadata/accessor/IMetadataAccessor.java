package com.g2forge.habitat.metadata.accessor;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMetadataAccessor {
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType);
}
