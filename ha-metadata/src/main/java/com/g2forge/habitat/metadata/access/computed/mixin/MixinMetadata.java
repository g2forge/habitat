package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.IPredicate1;
import com.g2forge.habitat.metadata.access.IApplicableMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class MixinMetadata implements IApplicableMetadataAccessor {
	@Default
	protected final IPredicate1<? super ISubjectType> subjectType = IPredicate1.create(true);

	@Default
	protected final IPredicate1<? super ISubject> subject = IPredicate1.create(true);

	protected final IPredicate1<? super IPredicateType<?>> predicateType;

	protected final IMetadataAccessor accessor;

	@Override
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType) {
		return new IPredicate<T>() {
			@Override
			public T get0() {
				final IMetadataAccessor accessor = getAccessor();
				if (accessor == null) return null;
				return accessor.bind(subject, predicateType).get0();
			}

			@Override
			public ISubject getSubject() {
				return subject;
			}

			@Override
			public IPredicateType<T> getType() {
				return predicateType;
			}

			@Override
			public boolean isPresent() {
				if (getAccessor() == null) return false;
				return getAccessor().bind(subject, predicateType).isPresent();
			}
		};
	}

	@Override
	public boolean isApplicable(ISubject subject, IPredicateType<?> predicateType) {
		if (!getSubject().test(subject)) return false;
		if (!getSubjectType().test(subject.getType())) return false;
		if (!getPredicateType().test(predicateType)) return false;
		return true;
	}
}
