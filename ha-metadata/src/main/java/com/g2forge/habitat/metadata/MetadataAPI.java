package com.g2forge.habitat.metadata;

import com.g2forge.habitat.metadata.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.predicate.GeneralPredicateType;
import com.g2forge.habitat.metadata.predicate.IPredicateType;
import com.g2forge.habitat.metadata.subject.ISubjectType;

public class MetadataAPI implements IMetadataAPI {
	@Override
	public <T> IPredicateType<T> predicate(Class<T> type) {
		if (type.isAnnotation()) return new AnnotationPredicateType<>(type);
		return new GeneralPredicateType<>(type);
	}

	@Override
	public ISubjectType subject(Void TODO) {
		// TODO Auto-generated method stub
		return null;
	}

}
