package com.g2forge.habitat.metadata.type.predicate;

import java.lang.annotation.Annotation;

import com.g2forge.alexandria.java.type.ref.ITypeRef;

public interface IAnnotationPredicateType<T extends Annotation> extends IPredicateType<T> {
	public Class<T> getAnnotationType();

	@Override
	public default ITypeRef<T> getObjectType() {
		return ITypeRef.of(getAnnotationType());
	}

	@Override
	public default Class<?> getPredicateID() {
		return getAnnotationType();
	}
}
