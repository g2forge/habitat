package com.g2forge.habitat.metadata.type.subject;

import java.util.Collection;

public interface IMergedSubjectType extends ISubjectType {
	public Collection<? extends ISubjectType> getTypes();
}
