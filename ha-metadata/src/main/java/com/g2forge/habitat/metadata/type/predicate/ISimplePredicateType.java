package com.g2forge.habitat.metadata.type.predicate;

public interface ISimplePredicateType<T> extends IPredicateType<T> {
	@Override
	public default Class<?> getPredicateID() {
		return getClass();
	}
}
