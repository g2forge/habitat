package com.g2forge.habitat.metadata.type;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;

public interface IMetadataTypeContext {
	public IMetadataValueContext getValueContext();

	public ISubjectType merge(Collection<? extends ISubjectType> types);

	public default ISubjectType merge(ISubjectType... types) {
		return merge(HCollection.asList(types));
	}

	public <T> IPredicateType<T> predicate(Class<T> type);

	public ISubjectType subject(Class<? extends AnnotatedElement> elementType, Class<?> valueType);
}
