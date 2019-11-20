package com.g2forge.habitat.metadata;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.habitat.metadata.value.IMergeableMetadataSubjectFactory;
import com.g2forge.habitat.metadata.value.IMetadataValue;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMetadata extends IMetadataValue, IMergeableMetadataSubjectFactory<ISubject> {
	@Override
	public default ISubject merge(Collection<? extends ISubject> subjects) {
		return getContext().merge(subjects);
	}

	@Override
	public default ISubject of(AnnotatedElement element, Object value) {
		return getContext().of(element, value);
	}
}
