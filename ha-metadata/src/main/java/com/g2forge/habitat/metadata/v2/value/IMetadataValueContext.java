package com.g2forge.habitat.metadata.v2.value;

import com.g2forge.habitat.metadata.v2.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.type.subject.ISubjectType;

public interface IMetadataValueContext {
	public IMetadataAccessor find(ISubjectType subjectType, IPredicateType<?> predicateType);
}
