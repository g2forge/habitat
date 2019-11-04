package com.g2forge.habitat.metadata.access.merged;

import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;
import com.g2forge.habitat.metadata.value.subject.MergedSubject;

class MergedMetadataAccessor implements IMetadataAccessor {
	@Override
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType) {
		if (!(subject instanceof MergedSubject)) throw new IllegalArgumentException(String.format("%1$s is not an element subject!", subject));
		return new MergedPredicate<>((MergedSubject) subject, predicateType);
	}
}