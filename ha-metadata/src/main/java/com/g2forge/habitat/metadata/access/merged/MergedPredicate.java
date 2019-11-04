package com.g2forge.habitat.metadata.access.merged;

import java.util.ArrayList;
import java.util.List;

import com.g2forge.habitat.metadata.type.predicate.IContainerPredicateType;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;
import com.g2forge.habitat.metadata.value.subject.MergedSubject;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class MergedPredicate<T> implements IPredicate<T> {
	protected final MergedSubject subject;

	protected final IPredicateType<T> type;

	@Override
	public T get0() {
		if (getType() instanceof IContainerPredicateType) {
			@SuppressWarnings("unchecked")
			final IContainerPredicateType<T, ?> cast = (IContainerPredicateType<T, ?>) getType();
			return merge(cast);
		} else {
			for (ISubject subject : getSubject().getSubjects()) {
				final IPredicate<T> bound = subject.bind(getType());
				if (bound.isPresent()) return bound.get0();
			}
			return null;
		}
	}

	protected <U> T merge(IContainerPredicateType<T, U> predicateType) {
		final List<U> retVal = new ArrayList<>();
		for (ISubject subject : getSubject().getSubjects()) {
			final IPredicate<T> bound = subject.bind(getType());
			if (bound.isPresent()) retVal.addAll(predicateType.unwrap(bound.get0()));
		}
		return predicateType.wrap(retVal);
	}

	@Override
	public boolean isPresent() {
		return getSubject().getSubjects().stream().map(s -> s.bind(getType())).anyMatch(IPredicate::isPresent);
	}
}