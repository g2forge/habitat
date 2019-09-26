package com.g2forge.habitat.metadata.accessor;

import com.g2forge.habitat.metadata.predicate.IPredicate;
import com.g2forge.habitat.metadata.predicate.IPredicateType;
import com.g2forge.habitat.metadata.subject.ISubject;

public interface IMetadataAccessor {
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType);
}
