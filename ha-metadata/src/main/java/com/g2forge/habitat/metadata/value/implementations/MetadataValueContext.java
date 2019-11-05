package com.g2forge.habitat.metadata.value.implementations;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.IMetadataRegistry.IFindContext;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MetadataValueContext implements IMetadataValueContext {
	protected final IMetadataTypeContext typeContext;

	@Getter(AccessLevel.PROTECTED)
	protected final IMetadataRegistry registry;

	@Override
	public IMetadataAccessor find(ISubjectType subjectType, IPredicateType<?> predicateType) {
		return getRegistry().find(new IFindContext() {}, subjectType, predicateType);
	}

	@Override
	public ISubject merge(Collection<? extends ISubject> subjects) {
		return new MergedSubject(this, subjects);
	}

	@Override
	public ISubject of(AnnotatedElement element, Object value) {
		if (element == null) {
			if (value == null) throw new NullPointerException("Cannot get metadata for a null element and value!");
			if (value instanceof AnnotatedElement) return of((AnnotatedElement) value, null);
			else return new ValueSubject(this, value);
		} else {
			if (value == null) return new ElementSubject(this, element);
			return new ElementValueSubject(this, element, value);
		}
	}
}