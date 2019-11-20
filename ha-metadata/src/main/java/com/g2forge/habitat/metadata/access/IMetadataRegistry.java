package com.g2forge.habitat.metadata.access;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMetadataRegistry {
	/**
	 * Find an appropriate accessor in this registry.
	 * 
	 * @param subject The subject we're trying to access.
	 * @param predicateType The type of the predicate we're trying to access.
	 * @return An appropriate accessor for the specified subject and predicate type.
	 * @throws NoAccessorFoundException If this registry has no appropriate accessor. Any other exception denotes a failure, this one denotes that the caller
	 *             may wish to look in another registry.
	 */
	public IMetadataAccessor find(ISubject subject, IPredicateType<?> predicateType);
}
