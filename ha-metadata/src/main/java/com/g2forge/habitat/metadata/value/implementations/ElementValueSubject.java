package com.g2forge.habitat.metadata.value.implementations;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.IValueSubject;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@ToString(exclude = "context")
public class ElementValueSubject implements IValueSubject {
	protected final IMetadataValueContext context;

	protected final AnnotatedElement element;

	protected final Object value;

	@Getter(lazy = true)
	private final ISubjectType type = computeType();

	protected ISubjectType computeType() {
		final AnnotatedElement element = getElement();
		return getContext().getTypeContext().subject(element == null ? null : element.getClass(), getValue().getClass());
	}
}
