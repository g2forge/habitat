package com.g2forge.habitat.metadata.type.implementations;

import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.subject.IAnnotatedSubjectType;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@ToString(exclude = "context")
class ElementSubjectType implements IAnnotatedSubjectType {
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

		public static ElementSubjectType.Type valueOf(Class<? extends AnnotatedElement> type) {
			for (Type retVal : values()) {
				if (retVal.getType() == null) continue;
				if (retVal.getType().isAssignableFrom(type)) return retVal;
			}
			throw new IllegalArgumentException(String.format("Type %1$s is not a known element type!", type));
		}

		protected final ElementType elementType;

		protected final Class<? extends AnnotatedElement> type;
	}

	public static ElementSubjectType valueOf(IMetadataTypeContext context, Class<? extends AnnotatedElement> type) {
		return new ElementSubjectType(context, ElementSubjectType.Type.valueOf(type));
	}

	protected final IMetadataTypeContext context;

	protected final Type type;
}
