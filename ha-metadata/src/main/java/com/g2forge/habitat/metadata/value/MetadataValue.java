package com.g2forge.habitat.metadata.value;

import java.lang.reflect.AnnotatedElement;
import java.util.stream.Collectors;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.java.core.helpers.HStream;
import com.g2forge.habitat.metadata.accessor.IMetadataAccessor;
import com.g2forge.habitat.metadata.annotations.ElementJavaAnnotations;
import com.g2forge.habitat.metadata.annotations.IJavaAnnotations;
import com.g2forge.habitat.metadata.annotations.MergedJavaAnnotations;
import com.g2forge.habitat.metadata.annotations.MergedJavaAnnotations.MergedJavaAnnotationsBuilder;
import com.g2forge.habitat.metadata.value.subject.ISubject;
import com.g2forge.habitat.metadata.value.subject.Subject;
import com.g2forge.habitat.metadata.annotations.ValueJavaAnnotations;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MetadataValue implements IMetadataValue {
	@Getter(AccessLevel.PROTECTED)
	protected final IMetadataAccessor metadataAccessor;

	@Override
	public ISubject merge(ISubject... subjects) {
		return merge(HCollection.asList(subjects));
	}

	@Override
	public ISubject merge(Iterable<? extends ISubject> subjects) {
		return new Subject(getMetadataAccessor(), new MergedJavaAnnotations(HStream.toStream(subjects.iterator()).map(s -> {
			final Subject subject = (Subject) s;
			if (getMetadataAccessor() != subject.getAccessor()) throw new IllegalArgumentException();
			return subject.getAnnotations();
		}).collect(Collectors.toList())));
	}

	@Override
	public ISubject of(AnnotatedElement element) {
		return of(new ElementJavaAnnotations(element));
	}

	@Override
	public ISubject of(AnnotatedElement element, Object value) {
		final MergedJavaAnnotationsBuilder builder = MergedJavaAnnotations.builder();
		if (element != null) builder.annotations(new ElementJavaAnnotations(element));
		if (value != null) builder.annotations(new ValueJavaAnnotations(value));
		return of(builder.build());
	}

	protected ISubject of(final IJavaAnnotations javaAnnotations) {
		return new Subject(getMetadataAccessor(), javaAnnotations);
	}

	@Override
	public ISubject of(Object value) {
		return of(new ValueJavaAnnotations(value));
	}
}
