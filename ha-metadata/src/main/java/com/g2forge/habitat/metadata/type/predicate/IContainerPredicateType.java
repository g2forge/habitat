package com.g2forge.habitat.metadata.type.predicate;

import java.util.List;

public interface IContainerPredicateType<T, U> extends IPredicateType<T> {
	public IPredicateType<U> getRepeatable();

	public List<U> unwrap(T value);

	public T wrap(List<U> list);
}
