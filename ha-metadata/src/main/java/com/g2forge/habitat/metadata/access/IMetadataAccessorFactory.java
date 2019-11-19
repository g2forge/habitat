package com.g2forge.habitat.metadata.access;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public interface IMetadataAccessorFactory {
	public IMetadataAccessor find(ISubjectType subjectType, IPredicateType<?> predicateType);
}
