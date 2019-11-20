package com.g2forge.habitat.metadata.value.implementations;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.value.IMetadataSubjectFactory;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;
import com.g2forge.habitat.metadata.value.subject.SubjectDescriptor;

public abstract class AMetadataSubjectFactory implements IMetadataSubjectFactory<ISubject> {
	protected abstract IMetadataValueContext getValueContext();

	@Override
	public ISubject of(AnnotatedElement element, Object value) {
		final SubjectDescriptor descriptor = new SubjectDescriptor(element, value).reduce();
		if (descriptor.getValue() == null) return new ElementSubject(getValueContext(), descriptor.getElement());
		return new ElementValueSubject(getValueContext(), descriptor.getElement(), descriptor.getValue());
	}
}