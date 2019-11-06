package com.g2forge.habitat.metadata.access.value;

import java.util.ArrayList;
import java.util.List;

import com.g2forge.alexandria.java.function.IFunction1;
import com.g2forge.alexandria.java.function.IPredicate1;
import com.g2forge.alexandria.java.function.ISupplier;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;
import com.g2forge.habitat.metadata.value.subject.IValueSubject;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class ValuePredicate<T> implements IPredicate<T> {
	protected static <I, O> O first(IPredicate1<? super I> predicate, IFunction1<? super I, ? extends O> function, ISupplier<? extends O> noMatch, Iterable<? extends ISupplier<? extends I>> suppliers) {
		for (ISupplier<? extends I> supplier : suppliers) {
			final I value = supplier.get();
			if (predicate.test(value)) return function.apply(supplier.get());
		}
		return noMatch.get();
	}

	protected final IValueSubject subject;

	protected final IPredicateType<T> type;

	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	private final IPredicate<T> delegate = computeDelegate();

	protected IPredicate<T> computeDelegate() {
		final IMetadataValueContext context = getContext();
		final IValueSubject subject = getSubject();
		final IPredicateType<T> predicateType = getType();

		final List<ISupplier<? extends IPredicate<T>>> suppliers = new ArrayList<>();
		// TODO: VALUE ONLY
		suppliers.add(() -> context.of(subject.getType().getValue(), null).bind(predicateType));
		if (subject.getElement() != null) suppliers.add(() -> context.of(subject.getElement(), null).bind(predicateType));

		return first(IPredicate::isPresent, IFunction1.identity(), () -> new IPredicate<T>() {
			@Override
			public T get0() {
				return null;
			}

			@Override
			public ISubject getSubject() {
				return ValuePredicate.this.getSubject();
			}

			@Override
			public IPredicateType<T> getType() {
				return ValuePredicate.this.getType();
			}

			@Override
			public boolean isPresent() {
				return false;
			}
		}, suppliers);
	}

	@Override
	public T get0() {
		return getDelegate().get0();
	}

	@Override
	public boolean isPresent() {
		return getDelegate().isPresent();
	}
}