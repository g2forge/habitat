package com.g2forge.habitat.metadata.type.implementations;

import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@ToString(exclude = "context")
class ValueSubjectType implements ISubjectType {
	protected final IMetadataTypeContext context;
}
