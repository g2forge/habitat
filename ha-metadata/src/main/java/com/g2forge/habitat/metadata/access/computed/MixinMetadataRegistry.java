package com.g2forge.habitat.metadata.access.computed;

import java.util.Map;

import com.g2forge.alexandria.java.function.IPredicate2;
import com.g2forge.alexandria.java.function.ISupplier;
import com.g2forge.alexandria.java.function.builder.IBuilder;
import com.g2forge.alexandria.java.function.builder.IModifier;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataPredicateFactory;
import com.g2forge.habitat.metadata.value.IMetadataSubjectFactory;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Builder(toBuilder = true)
public class MixinMetadataRegistry implements IMetadataRegistry {
	public interface IPredicateModifier extends IModifier<MixinMetadataRegistryBuilder>, IMetadataPredicateFactory<IValueModifier<?>> {
		@Override
		public <T> IValueModifier<T> bind(Class<T> type);

		@Override
		public <T> IValueModifier<T> bind(IPredicateType<T> predicateType);
	}

	public interface ISubjectModifier extends IModifier<MixinMetadataRegistryBuilder>, IMetadataSubjectFactory<IPredicateModifier> {}

	public interface IValueModifier<T> extends IModifier<MixinMetadataRegistryBuilder> {
		public MixinMetadataRegistryBuilder absent();

		public MixinMetadataRegistryBuilder functional(ISupplier<? super T> supplier);

		public MixinMetadataRegistryBuilder set(T value);
	}

	public static class MixinMetadataRegistryBuilder implements IBuilder<MixinMetadataRegistry> {
		public <T> MixinMetadataRegistryBuilder accessorTyped(ITypedMetadataAccessor<T, ? extends ISubject, ? extends IPredicateType<T>> accessor) {
			accessor(accessor::isApplicable, (IMetadataAccessor) accessor);
			return this;
		}

		public ISubjectModifier subject() {
			return new SubjectModifier(this);
		}
	}

	@Singular
	@Getter(AccessLevel.PROTECTED)
	protected final Map<IPredicate2<? super ISubjectType, ? super IPredicateType<?>>, IMetadataAccessor> accessors;

	@Override
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		for (Map.Entry<IPredicate2<? super ISubjectType, ? super IPredicateType<?>>, IMetadataAccessor> entry : getAccessors().entrySet()) {
			if (entry.getKey().test(subjectType, predicateType)) return entry.getValue();
		}
		throw new NoAccessorFoundException("No matching accessors found");
	}
}
