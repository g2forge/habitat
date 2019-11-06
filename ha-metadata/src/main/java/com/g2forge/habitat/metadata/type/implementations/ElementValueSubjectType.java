package com.g2forge.habitat.metadata.type.implementations;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.subject.IValueSubjectType;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
class ElementValueSubjectType implements IValueSubjectType {
	@ToString.Exclude
	protected final IMetadataTypeContext context;
	
	protected final Class<? extends AnnotatedElement> element;
	
	protected final Class<?> value;

	@Getter(lazy = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private final ElementSubjectType.Type type = ElementSubjectType.Type.valueOf(getElement());

	@Override
	public boolean isValueOnly() {
		return getElement() == null;
	}
}
