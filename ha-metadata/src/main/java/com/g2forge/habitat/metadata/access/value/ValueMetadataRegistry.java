package com.g2forge.habitat.metadata.access.value;

import com.g2forge.habitat.metadata.access.AMetadataRegistry;
import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.type.subject.IValueSubjectType;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public class ValueMetadataRegistry extends AMetadataRegistry<IValueSubjectType> {
	@Getter(value = AccessLevel.PROTECTED, lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final ITypedMetadataAccessor accessor = new ValueMetadataAccessor();
}
