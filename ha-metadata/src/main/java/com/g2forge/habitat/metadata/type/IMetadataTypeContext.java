package com.g2forge.habitat.metadata.type;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;

public interface IMetadataTypeContext extends IMergeableMetadataSubjectTypeFactory<ISubjectType>, IMetadataPredicateTypeFactory<IPredicateType<?>> {
	public IMetadataValueContext getValueContext();

	@Override
	public <T> IPredicateType<T> predicate(Class<T> type);
}
