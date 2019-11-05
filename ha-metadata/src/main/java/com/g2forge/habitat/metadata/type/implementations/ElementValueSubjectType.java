package com.g2forge.habitat.metadata.type.implementations;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@ToString(exclude = "context")
class ElementValueSubjectType implements ISubjectType {
	public static ElementValueSubjectType valueOf(IMetadataTypeContext context, Class<? extends AnnotatedElement> type) {
		for (ElementSubjectType.Type retVal : ElementSubjectType.Type.values()) {
			if (retVal.getType() == null) continue;
			if (retVal.getType().isAssignableFrom(type)) return new ElementValueSubjectType(context, retVal);
		}
		throw new IllegalArgumentException(String.format("Type %1$s is not a known element type!", type));
	}

	protected final IMetadataTypeContext context;

	protected final ElementSubjectType.Type type;
}
