package com.g2forge.habitat.metadata.v2.access;

import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.type.subject.ISubjectType;

public interface IMetadataRegistry {
	public interface IFindContext {}

	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType);
}
