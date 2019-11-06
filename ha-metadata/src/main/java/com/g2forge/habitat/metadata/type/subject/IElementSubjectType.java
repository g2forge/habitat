package com.g2forge.habitat.metadata.type.subject;

import java.lang.reflect.AnnotatedElement;

public interface IElementSubjectType extends ISubjectType {
	public Class<? extends AnnotatedElement> getElement();
}
