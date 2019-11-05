package com.g2forge.habitat.metadata.value.implementations;

import java.util.Collection;
import java.util.stream.Collectors;

import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.IMergedSubject;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class MergedSubject implements IMergedSubject {
	protected final IMetadataValueContext context;

	protected final Collection<? extends ISubject> subjects;

	@Getter(lazy = true)
	private final ISubjectType type = getContext().getTypeContext().merge(getSubjects().stream().map(ISubject::getType).collect(Collectors.toList()));
}
