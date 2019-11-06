package com.g2forge.habitat.metadata.access.merged;

import com.g2forge.habitat.metadata.access.AMetadataRegistry;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.subject.IMergedSubjectType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public class MergedMetadataRegistry extends AMetadataRegistry {
	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final IMetadataAccessor accessor = new MergedMetadataAccessor();

	@Override
	protected void check(ISubjectType subjectType) {
		if (!(subjectType instanceof IMergedSubjectType)) throw new NoAccessorFoundException(String.format("%1$s is not an merged subject type!", subjectType));
	}
}