package com.g2forge.habitat.metadata.value.implementations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.annotations.IJavaAnnotated;
import com.g2forge.habitat.metadata.annotations.implementations.ElementJavaAnnotated;
import com.g2forge.habitat.metadata.type.subject.IValueSubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.IValueSubject;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ElementValueSubject implements IValueSubject {
	@ToString.Exclude
	protected final IMetadataValueContext context;

	protected final AnnotatedElement element;

	protected final Object value;

	@Getter(lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final IValueSubjectType type = computeType();

	@Getter(lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final IJavaAnnotated annotated = new ElementJavaAnnotated(getElement());

	protected IValueSubjectType computeType() {
		final AnnotatedElement element = getElement();
		final Class<? extends AnnotatedElement> elementType = element == null ? null : element.getClass();

		final Object value = getValue();
		final Class<?> valueType = (value instanceof Annotation) ? ((Annotation) value).annotationType() : value.getClass();

		return (IValueSubjectType) getContext().getTypeContext().subject(elementType, valueType);
	}
}
