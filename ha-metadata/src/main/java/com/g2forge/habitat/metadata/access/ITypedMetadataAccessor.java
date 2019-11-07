package com.g2forge.habitat.metadata.access;

import java.lang.reflect.Type;

import com.g2forge.alexandria.java.reflect.HReflection;
import com.g2forge.alexandria.java.type.ref.ATypeRefIdentity;
import com.g2forge.alexandria.java.type.ref.ITypeRef;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface ITypedMetadataAccessor<T, S extends ISubject, PT extends IPredicateType<T>> extends IMetadataAccessor {
	@Override
	public default <_T> IPredicate<_T> bind(ISubject subject, IPredicateType<_T> predicateType) {
		if (!getSubjectType().isInstance(subject)) throw new IllegalArgumentException(String.format("Subject %1$s is not an instance of %2$s!", subject, getSubjectType()));
		if (!getPredicateTypeType().isInstance(predicateType)) throw new IllegalArgumentException(String.format("Predicate type %1$s is not an instance of %2$s!", predicateType, getPredicateTypeType()));
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final IPredicate<_T> retVal = (IPredicate) this.bindTyped((S) subject, (PT) predicateType);
		return retVal;
	}

	public IPredicate<T> bindTyped(S subject, PT predicateType);

	public default ITypeRef<PT> getPredicateTypeType() {
		return new ATypeRefIdentity<PT>() {
			@Override
			public Type getType() {
				return HReflection.getParentTypeArgument(ITypedMetadataAccessor.this, ITypedMetadataAccessor.class, 2);
			}
		};
	}

	public default ITypeRef<S> getSubjectType() {
		return new ATypeRefIdentity<S>() {
			@Override
			public Type getType() {
				return HReflection.getParentTypeArgument(ITypedMetadataAccessor.this, ITypedMetadataAccessor.class, 1);
			}
		};
	}
}