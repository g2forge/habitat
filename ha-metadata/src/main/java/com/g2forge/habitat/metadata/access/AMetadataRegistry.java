package com.g2forge.habitat.metadata.access;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public abstract class AMetadataRegistry implements IMetadataRegistry {
	protected void check(IPredicateType<?> predicateType) {}

	protected void check(ISubject subject) {}

	@Override
	public IMetadataAccessor find(ISubject subject, IPredicateType<?> predicateType) {
		final ITypedMetadataAccessor<?, ?, ?> accessor = getAccessor();
		accessor.check(subject, predicateType);
		check(subject);
		check(predicateType);
		return accessor;
	}

	protected abstract ITypedMetadataAccessor<?, ?, ?> getAccessor();
}
