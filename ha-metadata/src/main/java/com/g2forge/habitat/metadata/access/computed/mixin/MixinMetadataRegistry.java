package com.g2forge.habitat.metadata.access.computed.mixin;

import java.util.List;

import com.g2forge.alexandria.java.function.builder.IBuilder;
import com.g2forge.habitat.metadata.access.IApplicableMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.access.computed.IComputedMetadataRegistry;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Builder(toBuilder = true)
public class MixinMetadataRegistry implements IComputedMetadataRegistry {
	public static class MixinMetadataRegistryBuilder implements IBuilder<MixinMetadataRegistry> {
		public ISubjectModifier subject() {
			return new SubjectModifier(this);
		}
	}

	@Singular
	@Getter(AccessLevel.PROTECTED)
	protected final List<IApplicableMetadataAccessor> accessors;

	@Override
	public IMetadataAccessor find(ISubject subject, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		for (IApplicableMetadataAccessor accessor : getAccessors()) {
			if (accessor.isApplicable(subject, predicateType)) return accessor;
		}
		throw new NoAccessorFoundException("No matching accessors found");
	}
}
