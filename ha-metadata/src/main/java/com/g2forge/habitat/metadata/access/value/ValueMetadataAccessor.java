package com.g2forge.habitat.metadata.access.value;

import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.IValueSubject;

class ValueMetadataAccessor<T> implements ITypedMetadataAccessor<T, IValueSubject, IPredicateType<T>> {
	@Override
	public IPredicate<T> bindTyped(IValueSubject subject, IPredicateType<T> predicateType) {
		return new ValuePredicate<>(subject, predicateType);
	}
}
