package com.g2forge.habitat.metadata.type.predicate;

import com.g2forge.alexandria.java.type.ref.ITypeRef;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class GeneralPredicateType<T> implements IPredicateType<T> {
	protected final ITypeRef<T> objectType;

	protected final Class<?> predicateID;
}
