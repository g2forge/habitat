package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.IPredicate1;
import com.g2forge.alexandria.java.function.ISupplier;
import com.g2forge.habitat.metadata.access.IApplicableMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
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
public class MixinMetadataAccessor implements IApplicableMetadataAccessor {
	@Default
	protected final IPredicate1<? super ISubjectType> subjectType = IPredicate1.create(true);

	@Default
	protected final IPredicate1<? super ISubject> subject = IPredicate1.create(true);

	protected final IPredicate1<? super IPredicateType<?>> predicateType;

	protected final ISupplier<?> supplier;

	@Override
	public <T> IPredicate<T> bind(ISubject subject, IPredicateType<T> predicateType) {
		return new IPredicate<T>() {
			@Override
			public T get0() {
				final ISupplier<?> supplier = getSupplier();
				if (supplier == null) return null;
				@SuppressWarnings("unchecked")
				final T retVal = (T) supplier.get();
				return retVal;
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
				return getSupplier() != null;
			}
		};
	}

	@Override
	public void check(IMetadataRegistry.IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		if (!getSubjectType().test(subjectType)) throw new NoAccessorFoundException(String.format("Subject type (%1$s) is not acceptable", subjectType));
		if (!getPredicateType().test(predicateType)) throw new NoAccessorFoundException(String.format("Predicate type (%1$s) is not acceptable", predicateType));
	}

	@Override
	public boolean isApplicable(IMetadataRegistry.IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) {
		if (!getSubjectType().test(subjectType)) return false;
		if (!getPredicateType().test(predicateType)) return false;
		return true;
	}
}
