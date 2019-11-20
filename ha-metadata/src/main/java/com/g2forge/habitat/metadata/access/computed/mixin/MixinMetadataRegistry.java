package com.g2forge.habitat.metadata.access.computed.mixin;

import java.util.Map;

import com.g2forge.alexandria.java.function.IFunction1;
import com.g2forge.alexandria.java.function.IPredicate3;
import com.g2forge.alexandria.java.function.builder.IBuilder;
import com.g2forge.habitat.metadata.access.IApplicableMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.access.computed.IComputedMetadataRegistry;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Builder(toBuilder = true)
public class MixinMetadataRegistry implements IComputedMetadataRegistry {
	public static class MixinMetadataRegistryBuilder implements IBuilder<MixinMetadataRegistry> {
		public MixinMetadataRegistryBuilder accessor(IApplicableMetadataAccessor accessor) {
			factory(accessor::isApplicable, c -> (IMetadataAccessor) accessor);
			return this;
		}

		public ISubjectModifier subject() {
			return new SubjectModifier(this);
		}
	}

	@Singular
	@Getter(AccessLevel.PROTECTED)
	protected final Map<IPredicate3<? super IMetadataRegistry.IFindContext, ? super ISubjectType, ? super IPredicateType<?>>, IFunction1<? super IFindContext, ? extends IMetadataAccessor>> factories;

	@Override
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		for (Map.Entry<IPredicate3<? super IMetadataRegistry.IFindContext, ? super ISubjectType, ? super IPredicateType<?>>, IFunction1<? super IFindContext, ? extends IMetadataAccessor>> entry : getFactories().entrySet()) {
			if (entry.getKey().test(context, subjectType, predicateType)) return entry.getValue().apply(context);
		}
		throw new NoAccessorFoundException("No matching accessors found");
	}
}
