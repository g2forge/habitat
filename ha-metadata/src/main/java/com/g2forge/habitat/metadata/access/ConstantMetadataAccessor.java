package com.g2forge.habitat.metadata.access;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.ConstantPredicate;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ConstantMetadataAccessor implements IMetadataAccessor {
	protected final Object value;

	protected final boolean present;

	@Override
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType) {
		final Object value = getValue();
		if ((value != null) && !predicateType.getObjectType().isInstance(value)) throw new IllegalArgumentException("Predicate type does not match!");
		@SuppressWarnings("unchecked")
		final T cast = (T) value;
		return new ConstantPredicate<>(subject, predicateType, cast, isPresent());
	}
}
