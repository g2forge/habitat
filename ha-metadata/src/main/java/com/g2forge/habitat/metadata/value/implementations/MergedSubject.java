package com.g2forge.habitat.metadata.value.implementations;

import java.util.Collection;
import java.util.stream.Collectors;

import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.IMergedSubject;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class MergedSubject implements IMergedSubject {
	@ToString.Exclude
	protected final IMetadataValueContext context;

	protected final Collection<? extends ISubject> subjects;

	@Getter(lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final ISubjectType type = getContext().getTypeContext().merge(getSubjects().stream().map(ISubject::getType).collect(Collectors.toList()));
}
