package com.g2forge.habitat.metadata.value.predicate;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ConstantPredicate<T> extends APredicate<T> {
	public static <T> IPredicate<T> absent(ISubject subject, IPredicateType<T> type) {
		return new ConstantPredicate<>(subject, type, null, false);
	}

	public static <T> IPredicate<T> present(ISubject subject, IPredicateType<T> type, T value) {
		return new ConstantPredicate<>(subject, type, value, true);
	}

	protected final ISubject subject;

	protected final IPredicateType<T> type;

	protected final T value;

	protected final boolean present;

	@Override
	public T get() {
		return getValue();
	}
}
