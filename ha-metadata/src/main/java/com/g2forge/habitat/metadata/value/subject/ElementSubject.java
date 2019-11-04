package com.g2forge.habitat.metadata.value.subject;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ElementSubject implements ISubject {
	protected final IMetadataValueContext context;

	protected final AnnotatedElement element;

	@Getter(lazy = true)
	private final ISubjectType type = getContext().getTypeContext().subject(getElement().getClass());
}
