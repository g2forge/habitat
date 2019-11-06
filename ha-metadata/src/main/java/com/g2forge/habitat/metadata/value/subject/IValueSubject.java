package com.g2forge.habitat.metadata.value.subject;

import com.g2forge.habitat.metadata.type.subject.IValueSubjectType;

public interface IValueSubject extends IElementSubject {
	@Override
	public IValueSubjectType getType();

	public Object getValue();
}
