package com.g2forge.habitat.metadata.v2.type;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.habitat.metadata.v2.type.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.type.subject.ElementSubjectType;
import com.g2forge.habitat.metadata.v2.type.subject.ISubjectType;

public class MetadataTypeContext implements IMetadataTypeContext {
	@Override
	public <T> IPredicateType<T> predicate(Class<T> type) {
		if (Annotation.class.isAssignableFrom(type)) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final IPredicateType<T> retVal = new AnnotationPredicateType<>(this, (Class) type);
			return retVal;
		}
		throw new IllegalArgumentException(String.format("Type %1$s is not a valid predicate", type), new NotYetImplementedError());
	}

	@Override
	public ISubjectType subject(Class<?> type) {
		if (AnnotatedElement.class.isAssignableFrom(type)) return ElementSubjectType.valueOf(this, type.asSubclass(AnnotatedElement.class));
		throw new IllegalArgumentException(String.format("Type %1$s is not a valid subject", type), new NotYetImplementedError());
	}
}