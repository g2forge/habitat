package com.g2forge.habitat.metadata.value;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;

public interface IMetadataPredicateFactory<P> {
	public <T> P bind(Class<T> type);

	public <T> P bind(IPredicateType<T> predicateType);
}
