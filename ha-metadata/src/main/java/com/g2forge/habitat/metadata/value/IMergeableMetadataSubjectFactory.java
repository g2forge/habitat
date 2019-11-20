package com.g2forge.habitat.metadata.value;

import java.util.Collection;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMergeableMetadataSubjectFactory<S> extends IMetadataSubjectFactory<S> {
	public S merge(Collection<? extends ISubject> subjects);

	public default S merge(ISubject... subjects) {
		return merge(HCollection.asList(subjects));
	}
}
