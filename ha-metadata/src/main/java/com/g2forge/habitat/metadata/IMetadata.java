package com.g2forge.habitat.metadata;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.habitat.metadata.value.IMetadataSubjectFactory;
import com.g2forge.habitat.metadata.value.IMetadataValue;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMetadata extends IMetadataValue, IMetadataSubjectFactory {
	@Override
	public default ISubject merge(Collection<? extends ISubject> subjects) {
		return getContext().merge(subjects);
	}

	@Override
	public default ISubject merge(ISubject... subjects) {
		return getContext().merge(subjects);
	}

	@Override
	public default ISubject of(AnnotatedElement element, Object value) {
		return getContext().of(element, value);
	}

	public default ISubject of(Object value) {
		return of(null, value);
	}
}
