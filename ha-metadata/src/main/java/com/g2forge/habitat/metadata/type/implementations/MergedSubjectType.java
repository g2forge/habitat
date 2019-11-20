package com.g2forge.habitat.metadata.type.implementations;

import java.util.Collection;

import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.subject.IMergedSubjectType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@ToString
class MergedSubjectType implements IMergedSubjectType {
	@ToString.Exclude
	protected final IMetadataTypeContext context;

	protected final Collection<? extends ISubjectType> types;
}
