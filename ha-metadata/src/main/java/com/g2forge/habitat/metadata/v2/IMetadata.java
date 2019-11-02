package com.g2forge.habitat.metadata.v2;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.habitat.metadata.v2.value.IMetadataSubjectFactory;
import com.g2forge.habitat.metadata.v2.value.IMetadataValue;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;

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
