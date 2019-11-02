package com.g2forge.habitat.metadata.v2.type;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;

public interface IMetadataTypeContext {
	public <T> IPredicateType<T> predicate(Class<T> type);
}
