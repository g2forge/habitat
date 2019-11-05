package com.g2forge.habitat.metadata.type.implementations;

import java.util.Collection;

import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.subject.IMergedSubjectType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class MergedSubjectType implements IMergedSubjectType {
	protected final IMetadataTypeContext context;

	protected final Collection<? extends ISubjectType> types;
}
