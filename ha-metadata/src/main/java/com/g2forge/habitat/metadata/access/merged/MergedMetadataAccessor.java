package com.g2forge.habitat.metadata.access.merged;

import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.IMergedSubject;

class MergedMetadataAccessor<T> implements ITypedMetadataAccessor<T, IMergedSubject, IPredicateType<T>> {
	@Override
	public IPredicate<T> bindTyped(IMergedSubject subject, IPredicateType<T> predicateType) {
		return new MergedPredicate<>(subject, predicateType);
	}
}