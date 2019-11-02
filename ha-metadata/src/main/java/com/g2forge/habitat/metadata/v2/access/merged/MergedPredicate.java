package com.g2forge.habitat.metadata.v2.access.merged;

import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.v2.value.subject.MergedSubject;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class MergedPredicate<T> implements IPredicate<T> {
	protected final MergedSubject subject;

	protected final IPredicateType<T> type;

	@Override
	public T get0() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPresent() {
		return getSubject().getSubjects().stream().map(s -> s.bind(getType())).anyMatch(IPredicate::isPresent);
	}
}