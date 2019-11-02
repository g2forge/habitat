package com.g2forge.habitat.metadata.type.predicate;

public interface ISelfPredicateType<T> extends IPredicateType<T> {
	@Override
	public default Class<?> getPredicateID() {
		return getClass();
	}
}
