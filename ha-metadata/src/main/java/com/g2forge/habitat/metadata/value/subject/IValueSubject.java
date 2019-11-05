package com.g2forge.habitat.metadata.value.subject;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.type.subject.IValueSubjectType;

public interface IValueSubject extends ISubject {
	@Override
	public IValueSubjectType getType();
	
	public AnnotatedElement getElement();
	
	public Object getValue();
}
