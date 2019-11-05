package com.g2forge.habitat.metadata.type.implementations;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.subject.IValueSubjectType;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@ToString(exclude = "context")
class ElementValueSubjectType implements IValueSubjectType {
	protected final IMetadataTypeContext context;
	
	protected final Class<? extends AnnotatedElement> element;
	
	protected final Class<?> value;

	@Getter(lazy = true)
	private final ElementSubjectType.Type type = ElementSubjectType.Type.valueOf(getElement());

	@Override
	public boolean isValueOnly() {
		return getElement() == null;
	}
}
