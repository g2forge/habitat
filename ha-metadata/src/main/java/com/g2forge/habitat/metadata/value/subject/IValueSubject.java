package com.g2forge.habitat.metadata.value.subject;

import java.lang.reflect.AnnotatedElement;

public interface IValueSubject extends ISubject {
	public AnnotatedElement getElement();
	
	public Object getValue();
}
