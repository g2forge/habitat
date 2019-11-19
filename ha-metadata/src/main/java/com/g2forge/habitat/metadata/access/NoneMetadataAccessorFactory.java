package com.g2forge.habitat.metadata.access;

import com.g2forge.alexandria.java.core.marker.ISingleton;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public class NoneMetadataAccessorFactory implements IMetadataAccessorFactory, ISingleton {
	protected static final NoneMetadataAccessorFactory INSTANCE = new NoneMetadataAccessorFactory();

	public static NoneMetadataAccessorFactory create() {
		return INSTANCE;
	}

	protected NoneMetadataAccessorFactory() {}

	@Override
	public IMetadataAccessor find(ISubjectType subjectType, IPredicateType<?> predicateType) {
		throw new NoAccessorFoundException();
	}
}
