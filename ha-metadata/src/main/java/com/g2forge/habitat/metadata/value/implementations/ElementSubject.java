package com.g2forge.habitat.metadata.value.implementations;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.annotations.IJavaAnnotated;
import com.g2forge.habitat.metadata.annotations.implementations.ElementJavaAnnotated;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.IAnnotatedSubject;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class ElementSubject implements IAnnotatedSubject {
	protected final IMetadataValueContext context;

	protected final AnnotatedElement element;

	@Getter(lazy = true)
	private final ISubjectType type = getContext().getTypeContext().subject(getElement().getClass());

	@Getter(lazy = true)
	private final IJavaAnnotated annotated = new ElementJavaAnnotated(getElement());
}
