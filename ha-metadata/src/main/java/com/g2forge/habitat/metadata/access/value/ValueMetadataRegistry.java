package com.g2forge.habitat.metadata.access.value;

import com.g2forge.habitat.metadata.access.AMetadataRegistry;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.type.subject.IValueSubjectType;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public class ValueMetadataRegistry extends AMetadataRegistry {
	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final IMetadataAccessor accessor = new ValueMetadataAccessor();

	protected void check(ISubjectType subjectType) {
		if (!(subjectType instanceof IValueSubjectType)) throw new NoAccessorFoundException(String.format("%1$s is not a value subject type!", subjectType));
	}
}
