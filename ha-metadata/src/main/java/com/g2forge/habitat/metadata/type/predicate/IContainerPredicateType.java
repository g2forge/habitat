package com.g2forge.habitat.metadata.type.predicate;

import com.g2forge.alexandria.collection.strategy.ICollectionStrategy;

public interface IContainerPredicateType<T, U> extends IPredicateType<T> {
	public ICollectionStrategy<T, U> getCollectionStrategy();
	
	public IPredicateType<U> getRepeatable();
}
