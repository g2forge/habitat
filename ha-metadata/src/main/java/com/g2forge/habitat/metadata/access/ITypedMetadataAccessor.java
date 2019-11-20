package com.g2forge.habitat.metadata.access;

import java.lang.reflect.Type;

import com.g2forge.alexandria.annotations.note.Note;
import com.g2forge.alexandria.annotations.note.NoteType;
import com.g2forge.alexandria.java.reflect.HReflection;
import com.g2forge.alexandria.java.type.ref.ATypeRefIdentity;
import com.g2forge.alexandria.java.type.ref.ITypeRef;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;
import com.g2forge.habitat.metadata.value.subject.SubjectType;

@Note(type = NoteType.TODO, value = "Abstract duplicate code structure in bind, check and isApplicable")
public interface ITypedMetadataAccessor<T, S extends ISubject, PT extends IPredicateType<T>> extends IApplicableMetadataAccessor {
	@Override
	public default <_T> IPredicate<_T> bind(ISubject subject, IPredicateType<_T> predicateType) {
		if (!getSubjectType().isInstance(subject)) throw new IllegalArgumentException(String.format("Subject %1$s is not an instance of %2$s!", subject, getSubjectType()));
		if (!getPredicateTypeType().isInstance(predicateType)) throw new IllegalArgumentException(String.format("Predicate type %1$s is not an instance of %2$s!", predicateType, getPredicateTypeType()));
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final IPredicate<_T> retVal = (IPredicate) this.bindTyped((S) subject, (PT) predicateType);
		return retVal;
	}

	public IPredicate<T> bindTyped(S subject, PT predicateType);

	public default void check(ISubject subject, IPredicateType<?> predicateType) {
		if (!getSubjectType().isInstance(subject)) throw new NoAccessorFoundException(String.format("Subject %1$s is not an instance of %2$s!", subject, getSubjectType()));
		final ITypeRef<? extends ISubjectType> subjectTypeType = ITypeRef.of(getSubjectType().getErasedType().getAnnotation(SubjectType.class).value());
		if (!subjectTypeType.isInstance(subject.getType())) throw new NoAccessorFoundException(String.format("Subject type %1$s is not an instance of %2$s!", subject.getType(), subjectTypeType));
		if (!getPredicateTypeType().isInstance(predicateType)) throw new NoAccessorFoundException(String.format("Predicate type %1$s is not an instance of %2$s!", predicateType, getPredicateTypeType()));
	}

	public default ITypeRef<T> getObjectType() {
		return new ATypeRefIdentity<T>() {
			@Override
			public Type getType() {
				return HReflection.getParentTypeArgument(ITypedMetadataAccessor.this, ITypedMetadataAccessor.class, 0);
			}
		};
	}

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

	@Override
	public default boolean isApplicable(ISubject subject, IPredicateType<?> predicateType) {
		final ITypeRef<S> subjectType = getSubjectType();
		final ITypeRef<? extends ISubjectType> subjectTypeType = ITypeRef.of(subjectType.getErasedType().getAnnotation(SubjectType.class).value());
		final ITypeRef<? extends IPredicateType<T>> predicateTypeType = getPredicateTypeType();
		return subjectType.isInstance(subject) && subjectTypeType.isInstance(subject.getType()) && predicateTypeType.isInstance(predicateType) && predicateType.getObjectType().isAssignableFrom(getObjectType());
	}
}