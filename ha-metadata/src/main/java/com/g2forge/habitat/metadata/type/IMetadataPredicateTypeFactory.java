package com.g2forge.habitat.metadata.type;

public interface IMetadataPredicateTypeFactory<P> {
	public <T> P predicate(Class<T> type);
}
