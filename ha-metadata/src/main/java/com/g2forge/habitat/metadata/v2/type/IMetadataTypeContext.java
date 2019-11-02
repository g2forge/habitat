package com.g2forge.habitat.metadata.v2.type;

import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.type.subject.ISubjectType;

public interface IMetadataTypeContext {
	public <T> IPredicateType<T> predicate(Class<T> type);

	public ISubjectType subject(Class<?> type);
}
