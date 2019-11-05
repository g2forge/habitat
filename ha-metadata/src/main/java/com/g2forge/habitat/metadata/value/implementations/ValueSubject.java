package com.g2forge.habitat.metadata.value.implementations;

import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@ToString(exclude = "context")
class ValueSubject implements ISubject {
	protected final IMetadataValueContext context;

	protected final Object value;

	@Override
	public ISubjectType getType() {
		return getContext().getTypeContext().subject(null, getValue().getClass());
	}
}
