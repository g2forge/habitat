package com.g2forge.habitat.metadata.v2.type.subject;

import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElementSubjectType implements ISubjectType {
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

	public static ElementSubjectType valueOf(Class<? extends AnnotatedElement> type) {
		for (ElementSubjectType retVal : values()) {
			if (retVal.getType() == null) continue;
			if (retVal.getType().isAssignableFrom(type)) return retVal;
		}
		throw new IllegalArgumentException(String.format("Type %1$s is not a known element type!", type));
	}

	protected final ElementType elementType;

	protected final Class<? extends AnnotatedElement> type;
}
