package com.g2forge.habitat.metadata.value.subject;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.annotations.IJavaAnnotated;
import com.g2forge.habitat.metadata.type.subject.IElementSubjectType;

@SubjectType(IElementSubjectType.class)
public interface IElementSubject extends ISubject {
	public IJavaAnnotated getAnnotated();

	public AnnotatedElement getElement();
}
