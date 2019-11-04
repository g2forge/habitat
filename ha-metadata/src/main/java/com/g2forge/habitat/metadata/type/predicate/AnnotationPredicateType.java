package com.g2forge.habitat.metadata.type.predicate;

import java.lang.annotation.Annotation;

import com.g2forge.alexandria.java.type.ref.ITypeRef;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class AnnotationPredicateType<T extends Annotation> implements IPredicateType<T>, IAnnotationPredicateType<T> {
	protected final IMetadataTypeContext context;
	
	protected final Class<T> annotationType;

	@Override
	public ITypeRef<T> getObjectType() {
		return ITypeRef.of(getAnnotationType());
	}

	@Override
	public Class<?> getPredicateID() {
		return getAnnotationType();
	}
}
