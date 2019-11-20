package com.g2forge.habitat.metadata.type;

import java.lang.reflect.AnnotatedElement;

public interface IMetadataSubjectTypeFactory<S> {
	public S subject(Class<? extends AnnotatedElement> elementType, Class<?> valueType);
}
