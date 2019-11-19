package com.g2forge.habitat.metadata.value;

import com.g2forge.habitat.metadata.access.IMetadataAccessorFactory;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IMetadataValueContext extends IMetadataSubjectFactory<ISubject>, IMetadataAccessorFactory {
	public IMetadataTypeContext getTypeContext();
}
