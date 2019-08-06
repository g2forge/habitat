package com.g2forge.habitat.metadata;

import java.lang.reflect.AnnotatedElement;

import com.g2forge.habitat.metadata.accessor.IMetadataAccessor;
import com.g2forge.habitat.metadata.annotations.ElementJavaAnnotations;
import com.g2forge.habitat.metadata.annotations.IJavaAnnotations;
import com.g2forge.habitat.metadata.annotations.MergedJavaAnnotations;
import com.g2forge.habitat.metadata.annotations.ValueJavaAnnotations;
import com.g2forge.habitat.metadata.subject.ISubject;
import com.g2forge.habitat.metadata.subject.Subject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MetadataContext implements IMetadataContext {
	@Getter(AccessLevel.PROTECTED)
	protected final IMetadataAccessor metadataAccessor;

	@Override
	public ISubject of(AnnotatedElement element) {
		return of(new ElementJavaAnnotations(element));
	}

	@Override
	public ISubject of(AnnotatedElement element, Object value) {
		return of(MergedJavaAnnotations.builder().annotations(new ElementJavaAnnotations(element)).annotations(new ValueJavaAnnotations(value)).build());
	}

	protected ISubject of(final IJavaAnnotations javaAnnotations) {
		return new Subject(getMetadataAccessor(), javaAnnotations);
	}

	@Override
	public ISubject of(Object value) {
		return of(new ValueJavaAnnotations(value));
	}
}
