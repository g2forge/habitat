package com.g2forge.habitat.metadata.predicate;

public interface ISimplePredicateType<T> extends IPredicateType<T> {
	@Override
	public default Class<?> getPredicateID() {
		return getClass();
	}
}
