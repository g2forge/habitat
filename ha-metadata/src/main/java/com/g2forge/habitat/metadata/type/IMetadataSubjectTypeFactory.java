package com.g2forge.habitat.metadata.type;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.alexandria.java.core.helpers.HCollection;

public interface IMetadataSubjectTypeFactory<S> {
	public S subject(Collection<? extends S> types);

	public default S subject(@SuppressWarnings("unchecked") S... types) {
		return subject(HCollection.asList(types));
	}

	public S subject(Class<? extends AnnotatedElement> elementType, Class<?> valueType);
}
