package com.g2forge.habitat.metadata.type.subject;

import java.lang.reflect.AnnotatedElement;

public interface IValueSubjectType extends ISubjectType {
	public Class<? extends AnnotatedElement> getElement();
	
	public Class<?> getValue();
	
	public boolean isValueOnly();
}
