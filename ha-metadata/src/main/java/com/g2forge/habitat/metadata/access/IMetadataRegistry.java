package com.g2forge.habitat.metadata.access;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public interface IMetadataRegistry {
	public interface IFindContext {
		public IMetadataAccessorFactory getDescendant();

		public IMetadataAccessorFactory getTop();
	}

	/**
	 * Find an appropriate accessor in this registry.
	 * 
	 * @param context The context of this find operation.
	 * @param subjectType The type of the subject we're trying to access.
	 * @param predicateType The type of the predicate we're trying to access.
	 * @return An appropriate accessor for the specified subject and predicate type.
	 * @throws NoAccessorFoundException If this registry has no appropriate accessor. Any other exception denotes a failure, this one denotes that the caller
	 *             may wish to look in another registry.
	 */
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) throws NoAccessorFoundException;
}
