package com.g2forge.habitat.metadata.access.merged;

import com.g2forge.habitat.metadata.access.AMetadataRegistry;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.type.subject.MergedSubjectType;

import lombok.AccessLevel;
import lombok.Getter;

public class MergedMetadataRegistry extends AMetadataRegistry {
	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	private final IMetadataAccessor accessor = new MergedMetadataAccessor();

	@Override
	protected void check(ISubjectType subjectType) {
		if (!(subjectType instanceof MergedSubjectType)) throw new NoAccessorFoundException(String.format("%1$s is not an merged subject type!", subjectType));
	}
}