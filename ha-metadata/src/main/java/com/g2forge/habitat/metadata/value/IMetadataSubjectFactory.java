package com.g2forge.habitat.metadata.value;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMetadataSubjectFactory<S> {
	public S merge(Collection<? extends ISubject> subjects);

	public default S merge(ISubject... subjects) {
		return merge(HCollection.asList(subjects));
	}

	public S of(AnnotatedElement element, Object value);

	public default S of(Object value) {
		return of(null, value);
	}
}
