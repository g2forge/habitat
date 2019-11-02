package com.g2forge.habitat.metadata.v2.type.predicate;

import com.g2forge.alexandria.java.type.ref.ITypeRef;
import com.g2forge.habitat.metadata.v2.type.IMetadataType;

public interface IPredicateType<T> extends IMetadataType {
	public ITypeRef<T> getObjectType();

	public Class<?> getPredicateID();
}
