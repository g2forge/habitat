package com.g2forge.habitat.metadata.type.subject;

public interface IValueSubjectType extends IElementSubjectType {
	public Class<?> getValue();

	public boolean isValueOnly();
}
