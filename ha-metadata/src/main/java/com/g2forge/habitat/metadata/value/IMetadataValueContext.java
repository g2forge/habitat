package com.g2forge.habitat.metadata.value;

import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMetadataValueContext extends IMetadataSubjectFactory<ISubject> {
	public IMetadataAccessor find(ISubjectType subjectType, IPredicateType<?> predicateType);

	public IMetadataTypeContext getTypeContext();
}
