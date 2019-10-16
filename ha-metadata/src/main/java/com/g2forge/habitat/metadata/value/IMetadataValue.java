package com.g2forge.habitat.metadata.value;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMetadataValue {
	public ISubject merge(Iterable<? extends ISubject> subjects);

	public ISubject merge(ISubject... subjects);

	public ISubject of(AnnotatedElement element);

	public ISubject of(AnnotatedElement element, Object value);

	public ISubject of(Object value);
}
