package com.g2forge.habitat.metadata;

import com.g2forge.habitat.metadata.predicate.IPredicateType;
import com.g2forge.habitat.metadata.subject.ISubjectType;

public interface IMetadataAPI {
	public <T> IPredicateType<T> predicate(Class<T> type);

	public ISubjectType subject(Void TODO);
}
