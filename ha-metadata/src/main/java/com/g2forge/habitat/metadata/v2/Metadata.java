package com.g2forge.habitat.metadata.v2;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.habitat.metadata.v2.access.annotation.AnnotationMetadataRegistry;
import com.g2forge.habitat.metadata.v2.type.MetadataTypeContext;
import com.g2forge.habitat.metadata.v2.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.v2.value.MetadataValueContext;
import com.g2forge.habitat.metadata.v2.value.subject.ElementSubject;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;

import lombok.Getter;

public class Metadata implements IMetadata {
	@Getter(lazy = true)
	private static final IMetadata standard = new Metadata();

	@Getter(lazy = true)
	private final IMetadataValueContext context = new MetadataValueContext(new MetadataTypeContext(), new AnnotationMetadataRegistry());

	@Override
	public ISubject of(AnnotatedElement element, Object value) {
		if ((element == null) && (value instanceof AnnotatedElement)) return of((AnnotatedElement) value, null);
		if ((value != null) || (element == null)) throw new NotYetImplementedError();

		return new ElementSubject(getContext(), element);
	}
}