package com.g2forge.habitat.metadata;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.subject.ISubject;

public interface IMetadataContext {
	public ISubject of(AnnotatedElement element);

	public ISubject of(AnnotatedElement element, Object value);

	public ISubject of(Object value);
}
