package com.g2forge.habitat.metadata.type;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public interface IMetadataTypeContext extends IMergeableMetadataSubjectTypeFactory<ISubjectType>, IMetadataPredicateTypeFactory<IPredicateType<?>> {
	@Override
	public <T> IPredicateType<T> predicate(Class<T> type);
}
