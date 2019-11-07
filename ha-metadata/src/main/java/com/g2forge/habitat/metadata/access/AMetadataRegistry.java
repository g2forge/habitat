package com.g2forge.habitat.metadata.access;

import java.lang.reflect.Type;

import com.g2forge.alexandria.java.reflect.HReflection;
import com.g2forge.alexandria.java.type.ref.ATypeRefIdentity;
import com.g2forge.alexandria.java.type.ref.ITypeRef;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public abstract class AMetadataRegistry<ST extends ISubjectType> implements IMetadataRegistry {
	protected void check(IPredicateType<?> predicateType) {}

	protected void check(ISubjectType subjectType) {}

	@Override
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) {
		if (context == null) throw new NullPointerException("The find context must be provided!");
		final ITypedMetadataAccessor<?, ?, ?> accessor = getAccessor();
		if (!getSubjectTypeType().isInstance(subjectType)) throw new NoAccessorFoundException(String.format("Subject type %1$s is not an instance of %2$s!", subjectType, getSubjectTypeType()));
		if (!accessor.getPredicateTypeType().isInstance(predicateType)) throw new NoAccessorFoundException(String.format("Predicate type %1$s is not an instance of %2$s!", predicateType, accessor.getPredicateTypeType()));
		check(subjectType);
		check(predicateType);
		return accessor;
	}

	protected abstract ITypedMetadataAccessor<?, ?, ?> getAccessor();

	protected ITypeRef<ST> getSubjectTypeType() {
		return new ATypeRefIdentity<ST>() {
			@Override
			public Type getType() {
				return HReflection.getParentTypeArgument(AMetadataRegistry.this, AMetadataRegistry.class, 0);
			}
		};
	}
}
