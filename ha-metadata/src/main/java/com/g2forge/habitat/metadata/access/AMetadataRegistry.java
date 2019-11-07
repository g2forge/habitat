package com.g2forge.habitat.metadata.access;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public abstract class AMetadataRegistry implements IMetadataRegistry {
	protected void check(IPredicateType<?> predicateType) {}

	protected void check(ISubjectType subjectType) {}

	@Override
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) {
		if (context == null) throw new NullPointerException("The find context must be provided!");
		final ITypedMetadataAccessor<?, ?, ?> accessor = getAccessor();
		accessor.check(subjectType, predicateType);
		check(subjectType);
		check(predicateType);
		return accessor;
	}

	protected abstract ITypedMetadataAccessor<?, ?, ?> getAccessor();
}
