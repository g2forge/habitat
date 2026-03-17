package com.g2forge.habitat.metadata.access.merged;

import com.g2forge.alexandria.collection.collector.ICollectionBuilder;
import com.g2forge.habitat.metadata.type.predicate.IContainerPredicateType;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.APredicate;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.IMergedSubject;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
class MergedPredicate<T> extends APredicate<T> {
	protected final IMergedSubject subject;

	protected final IPredicateType<T> type;

	@Override
	public T get() {
		if (getType() instanceof IContainerPredicateType) {
			final IContainerPredicateType<T, ?> cast = (IContainerPredicateType<T, ?>) getType();
			return merge(cast);
		} else {
			for (ISubject subject : getSubject().getSubjects()) {
				final IPredicate<T> bound = subject.bind(getType());
				if (bound.isPresent()) return bound.get();
			}
			return null;
		}
	}

	@Override
	public boolean isPresent() {
		return getSubject().getSubjects().stream().map(s -> s.bind(getType())).anyMatch(IPredicate::isPresent);
	}

	protected <U> T merge(IContainerPredicateType<T, U> predicateType) {
		final ICollectionBuilder<T, U> builder = predicateType.getCollectionStrategy().builder();
		for (ISubject subject : getSubject().getSubjects()) {
			final IPredicate<T> bound = subject.bind(getType());
			if (bound.isPresent()) builder.add(predicateType.getCollectionStrategy().iterable(bound.get()));
		}
		return builder.get();
	}
}