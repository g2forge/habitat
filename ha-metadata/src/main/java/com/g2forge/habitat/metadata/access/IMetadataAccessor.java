package com.g2forge.habitat.metadata.access;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMetadataAccessor {
	/**
	 * Method must first verify that the subject and predicateType are ones that it is prepared to look up. While this method should only ever be called after
	 * {@link com.g2forge.habitat.metadata.value.IMetadataValueContext#find(ISubject, IPredicateType)} the implementor of this interface should not assume that.
	 * 
	 * @param <T> The type of the object of the predicate.
	 * @param subject The subject to get metadata about.
	 * @param predicateType The type of the predicate for the metadata to be retrieved.
	 * @return A predicate representing the requested metadata.
	 */
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType);
}
