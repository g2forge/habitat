package com.g2forge.habitat.metadata.access.computed.mixin;

import java.util.Map;

import com.g2forge.alexandria.java.function.IPredicate2;
import com.g2forge.alexandria.java.function.builder.IBuilder;
import com.g2forge.habitat.metadata.access.IApplicableMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
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
		public MixinMetadataRegistryBuilder applicable(IApplicableMetadataAccessor accessor) {
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
