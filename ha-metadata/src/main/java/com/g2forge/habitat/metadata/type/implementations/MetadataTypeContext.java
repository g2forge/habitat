package com.g2forge.habitat.metadata.type.implementations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.habitat.metadata.annotations.ContainerAnnotationReflection;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public class MetadataTypeContext implements IMetadataTypeContext {
	@Override
	public ISubjectType merge(Collection<? extends ISubjectType> types) {
		return new MergedSubjectType(this, types);
	}

	@Override
	public <T> IPredicateType<T> predicate(Class<T> type) {
		if (Annotation.class.isAssignableFrom(type)) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final ContainerAnnotationReflection containerAnnotationReflection = ContainerAnnotationReflection.createIfContainer((Class) type);
			if (containerAnnotationReflection != null) {
				@SuppressWarnings("unchecked")
				final IPredicateType<T> retVal = new AnnotationContainerPredicateType<>(this, containerAnnotationReflection, predicate(containerAnnotationReflection.getRepeatable()));
				return retVal;
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			final IPredicateType<T> retVal = new AnnotationPredicateType<>(this, (Class) type);
			return retVal;
		}
		throw new IllegalArgumentException(String.format("Type %1$s is not a valid predicate", type), new NotYetImplementedError());
	}

	@Override
	public ISubjectType subject(Class<? extends AnnotatedElement> elementType, Class<?> valueType) {
		if ((elementType == null) && (valueType == null)) throw new NullPointerException("Cannot construct a subject type without either an element or value type!");
		if (valueType == null) return ElementSubjectType.valueOf(this, elementType);
		return new ElementValueSubjectType(this, elementType, valueType);
	}
}