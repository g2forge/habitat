package com.g2forge.habitat.metadata.access.merged;

import com.g2forge.habitat.metadata.access.AMetadataRegistry;
import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.type.subject.IMergedSubjectType;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public class MergedMetadataRegistry extends AMetadataRegistry<IMergedSubjectType> {
	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final ITypedMetadataAccessor accessor = new MergedMetadataAccessor();
}