package com.g2forge.habitat.metadata.v2.value;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;

public interface IMetadataSubjectFactory {
	public ISubject of(AnnotatedElement element, Object value);

	public default ISubject merge(ISubject... subjects) {
		return merge(HCollection.asList(subjects));
	}
	
	public ISubject merge(Collection<? extends ISubject> subjects);
}
