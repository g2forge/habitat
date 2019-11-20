package com.g2forge.habitat.metadata.access.computed.mixin;

import com.g2forge.alexandria.java.function.ISupplier;
import com.g2forge.habitat.metadata.access.ConstantMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.ConstantPredicate;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
class ValueModifier<T> implements IValueModifier<T> {
	protected final MixinMetadataRegistry.MixinMetadataRegistryBuilder builder;

	protected final MixinMetadataAccessor.MixinMetadataAccessorBuilder accessor;

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder absent() {
		return getBuilder().accessor(getAccessor().accessor(new ConstantMetadataAccessor(null, false)).build());
	}

	@Override
	public ICopyModifier copy() {
		return new CopyModifier(getBuilder(), getAccessor());
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder done() {
		return getBuilder();
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder set(T value) {
		return getBuilder().accessor(getAccessor().accessor(new ConstantMetadataAccessor(value, true)).build());
	}

	@Override
	public MixinMetadataRegistry.MixinMetadataRegistryBuilder supply(ISupplier<? super T> supplier) {
		if (supplier == null) throw new NullPointerException("Supplier cannot be null!");
		return getBuilder().accessor(getAccessor().accessor(new IMetadataAccessor() {
			@Override
			public <_T> IPredicate<_T> bind(ISubject subject, IPredicateType<_T> predicateType) {
				@SuppressWarnings("unchecked")
				final _T value = (_T) supplier.get();
				return new ConstantPredicate<>(subject, predicateType, value, true);
			}
		}).build());
	}
}
