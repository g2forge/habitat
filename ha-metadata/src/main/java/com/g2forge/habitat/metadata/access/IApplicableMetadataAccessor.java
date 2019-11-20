package com.g2forge.habitat.metadata.access;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IApplicableMetadataAccessor extends IMetadataAccessor {
	public boolean isApplicable(ISubject subject, IPredicateType<?> predicateType);
}