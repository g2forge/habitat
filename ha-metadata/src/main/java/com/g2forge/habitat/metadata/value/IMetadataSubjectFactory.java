package com.g2forge.habitat.metadata.value;

import java.lang.reflect.AnnotatedElement;

public interface IMetadataSubjectFactory<S> {
	public S of(AnnotatedElement element, Object value);

	public default S of(Object value) {
		return of(null, value);
	}
}
