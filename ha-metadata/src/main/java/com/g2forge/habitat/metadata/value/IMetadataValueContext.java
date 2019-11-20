package com.g2forge.habitat.metadata.value;

import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMetadataValueContext extends IMergeableMetadataSubjectFactory<ISubject>, IMetadataRegistry {
	public IMetadataTypeContext getTypeContext();
}
