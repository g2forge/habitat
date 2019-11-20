package com.g2forge.habitat.metadata.value.implementations;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.value.IMetadataSubjectFactory;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public abstract class AMetadataSubjectFactory implements IMetadataSubjectFactory<ISubject> {
	protected abstract IMetadataValueContext getValueContext();

	@Override
	public ISubject of(AnnotatedElement element, Object value) {
		if (element == null) {
			if (value == null) throw new NullPointerException("Cannot get metadata for a null element and value!");
			if (value instanceof AnnotatedElement) return of((AnnotatedElement) value, null);
		}

		if (value == null) return new ElementSubject(getValueContext(), element);
		return new ElementValueSubject(getValueContext(), element, value);
	}
}