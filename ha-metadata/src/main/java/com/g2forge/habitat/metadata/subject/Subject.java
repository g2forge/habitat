package com.g2forge.habitat.metadata.subject;

import com.g2forge.habitat.metadata.accessor.IMetadataAccessor;
import com.g2forge.habitat.metadata.annotations.IJavaAnnotations;
import com.g2forge.habitat.metadata.predicate.IPredicate;
import com.g2forge.habitat.metadata.predicate.IPredicateType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Subject implements ISubject {
	protected final IMetadataAccessor accessor;

	protected final IJavaAnnotations annotations;

	@Override
	public <T> IPredicate<T> bind(IPredicateType<T> predicateType) {
		return getAccessor().bind(this, getAnnotations(), predicateType);
	}
}
