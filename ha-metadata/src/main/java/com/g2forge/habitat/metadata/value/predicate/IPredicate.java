package com.g2forge.habitat.metadata.value.predicate;

import com.g2forge.alexandria.java.fluent.optional.IOptional;
import com.g2forge.alexandria.java.type.IGenericTyped;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.IMetadataValue;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IPredicate<T> extends IGenericTyped<T, IPredicateType<T>>, IOptional<T>, IMetadataValue {
	@Override
	public default IMetadataValueContext getContext() {
		return getSubject().getContext();
	}

	public ISubject getSubject();

	@Override
	public default boolean isEmpty() {
		return !isPresent();
	}

	@Override
	public default boolean isNotEmpty() {
		return isPresent();
	}

	public boolean isPresent();
}
