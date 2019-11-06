package com.g2forge.habitat.metadata.type.predicate;

import com.g2forge.habitat.metadata.access.indirect.IndirectMetadata;

public interface IIndirectPredicateType<T> extends IPredicateType<T> {
	public IndirectMetadata getIndirectMetadata();
}
