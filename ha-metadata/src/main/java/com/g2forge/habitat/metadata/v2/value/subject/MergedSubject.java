package com.g2forge.habitat.metadata.v2.value.subject;

import java.util.Collection;
import java.util.stream.Collectors;

import com.g2forge.habitat.metadata.v2.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.v2.value.IMetadataValueContext;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class MergedSubject implements ISubject {
	protected final IMetadataValueContext context;

	protected final Collection<? extends ISubject> subjects;

	@Getter(lazy = true)
	private final ISubjectType type = getContext().getTypeContext().merge(getSubjects().stream().map(ISubject::getType).collect(Collectors.toList()));
}
