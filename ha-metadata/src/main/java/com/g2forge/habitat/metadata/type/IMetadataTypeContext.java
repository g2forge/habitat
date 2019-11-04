package com.g2forge.habitat.metadata.type;

import java.util.Collection;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public interface IMetadataTypeContext {
	public <T> IPredicateType<T> predicate(Class<T> type);

	public ISubjectType subject(Class<?> type);
	
	public default ISubjectType merge(ISubjectType...types) {
		return merge(HCollection.asList(types));
	}
	
	public ISubjectType merge(Collection<? extends ISubjectType> types);
}
