package com.g2forge.habitat.metadata.v2;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.v2.value.IMetadataValue;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;

public interface IMetadata extends IMetadataValue {
	public ISubject of(AnnotatedElement element, Object value);

	public default ISubject of(Object value) {
		return of(null, value);
	}
}
