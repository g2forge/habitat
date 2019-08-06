package com.g2forge.habitat.metadata.predicate;

import java.lang.annotation.Annotation;

import com.g2forge.alexandria.java.type.ref.ITypeRef;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class AnnotationPredicateType<T extends Annotation> implements IPredicateType<T> {
	protected final Class<T> type;

	@Override
	public ITypeRef<T> getObjectType() {
		return ITypeRef.of(getType());
	}

	@Override
	public Class<?> getPredicateID() {
		return getType();
	}

}
