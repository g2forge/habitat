package com.g2forge.habitat.metadata.access.value;

import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;
import com.g2forge.habitat.metadata.value.subject.IValueSubject;

public class ValueMetadataAccessor implements IMetadataAccessor {
	@Override
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType) {
		return new ValuePredicate<>((IValueSubject) subject, predicateType);
	}
}
