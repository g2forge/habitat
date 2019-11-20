package com.g2forge.habitat.metadata.access;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public interface IApplicableMetadataAccessor extends IMetadataAccessor {
	public void check(IMetadataRegistry.IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) throws NoAccessorFoundException;

	public boolean isApplicable(IMetadataRegistry.IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType);
}