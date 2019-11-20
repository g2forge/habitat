package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.IPredicate2;
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
	protected final IPredicate2<? super ISubjectType, ? super TestContext> subjectType = IPredicate2.create(true);

	@Default
	protected final IPredicate2<? super ISubject, ? super TestContext> subject = IPredicate2.create(true);

	protected final IPredicate2<? super IPredicateType<?>, ? super TestContext> predicateType;

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
		final TestContext testContext = new TestContext(context);
		if (!getSubjectType().test(subjectType, testContext)) throw new NoAccessorFoundException(String.format("Subject type (%1$s) is not acceptable", subjectType));
		if (!getPredicateType().test(predicateType, testContext)) throw new NoAccessorFoundException(String.format("Predicate type (%1$s) is not acceptable", predicateType));
	}

	@Override
	public boolean isApplicable(IMetadataRegistry.IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) {
		final TestContext testContext = new TestContext(context);
		if (!getSubjectType().test(subjectType, testContext)) return false;
		if (!getPredicateType().test(predicateType, testContext)) return false;
		return true;
	}
}
