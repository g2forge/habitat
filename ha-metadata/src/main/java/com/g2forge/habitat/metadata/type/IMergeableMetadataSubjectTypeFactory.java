package com.g2forge.habitat.metadata.type;

import java.util.Collection;

import com.g2forge.alexandria.java.core.helpers.HCollection;

public interface IMergeableMetadataSubjectTypeFactory<S> extends IMetadataSubjectTypeFactory<S> {
	public S subject(Collection<? extends S> types);

	public default S subject(@SuppressWarnings("unchecked") S... types) {
		return subject(HCollection.asList(types));
	}
}
