package com.g2forge.habitat.metadata.type.implementations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.habitat.metadata.access.indirect.IndirectMetadata;
import com.g2forge.habitat.metadata.annotations.ContainerAnnotationReflection;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataSubjectFactory;
import com.g2forge.habitat.metadata.value.predicate.IPredicate;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MetadataTypeContext implements IMetadataTypeContext {
	@Getter(AccessLevel.PROTECTED)
	protected final IMetadataSubjectFactory<ISubject> metadataSubjectFactory;

	@Override
	public <T> IPredicateType<T> predicate(Class<T> type) {
		// Handle annotation predicate types
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

		// Handle indirect predicate types
		final IPredicate<IndirectMetadata> predicate = getMetadataSubjectFactory().of(type, null).bind(IndirectMetadata.class);
		if (predicate.isPresent() && (predicate.get0() != null)) return new IndirectPredicateType<>(this, type, predicate.get0());

		// Fall back to general predicate types
		return new PredicateType<>(this, type);
	}

	@Override
	public ISubjectType subject(Class<? extends AnnotatedElement> elementType, Class<?> valueType) {
		if ((elementType == null) && (valueType == null)) throw new NullPointerException("Cannot construct a subject type without either an element or value type!");
		if (valueType == null) return ElementSubjectType.valueOf(this, elementType);
		return new ElementValueSubjectType(this, elementType, valueType);
	}

	@Override
	public ISubjectType subject(Collection<? extends ISubjectType> types) {
		return new MergedSubjectType(this, types);
	}
}