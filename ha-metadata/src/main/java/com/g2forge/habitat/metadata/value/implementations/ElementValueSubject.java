package com.g2forge.habitat.metadata.value.implementations;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@ToString(exclude = "context")
public class ElementValueSubject implements ISubject {
	protected final IMetadataValueContext context;

	protected final AnnotatedElement element;
	
	protected final Object value;

	@Getter(lazy = true)
	private final ISubjectType type = getContext().getTypeContext().subject(getElement().getClass(), getValue().getClass());
}
