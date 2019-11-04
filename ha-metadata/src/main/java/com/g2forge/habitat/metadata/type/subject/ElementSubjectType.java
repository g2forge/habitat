package com.g2forge.habitat.metadata.type.subject;

import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.g2forge.habitat.metadata.type.IMetadataTypeContext;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ElementSubjectType implements ISubjectType {
	@Getter
	@RequiredArgsConstructor
	public enum Type {
		Type(ElementType.TYPE, Class.class),
		Field(ElementType.FIELD, Field.class),
		Method(ElementType.METHOD, Method.class),
		Parameter(ElementType.PARAMETER, Parameter.class),
		Constructor(ElementType.CONSTRUCTOR, Constructor.class),
		Variable(ElementType.LOCAL_VARIABLE, null),
		/*AnnotationType(ElementType.ANNOTATION_TYPE),
		Package(ElementType.PACKAGE),
		TypeParameter(ElementType.TYPE_PARAMETER),
		TypeUse(ElementType.TYPE_USE)*/;

		protected final ElementType elementType;

		protected final Class<? extends AnnotatedElement> type;
	}

	public static ElementSubjectType valueOf(IMetadataTypeContext context, Class<? extends AnnotatedElement> type) {
		for (Type retVal : Type.values()) {
			if (retVal.getType() == null) continue;
			if (retVal.getType().isAssignableFrom(type)) return new ElementSubjectType(context, retVal);
		}
		throw new IllegalArgumentException(String.format("Type %1$s is not a known element type!", type));
	}

	protected final IMetadataTypeContext context;

	protected final Type type;
}
