package com.g2forge.habitat.metadata.v2.type.subject;

import java.util.Collection;

import com.g2forge.habitat.metadata.v2.type.IMetadataTypeContext;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class MergedSubjectType implements ISubjectType {
	protected final IMetadataTypeContext context;

	protected final Collection<? extends ISubjectType> types;
}
