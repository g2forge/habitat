package com.g2forge.habitat.metadata.v2.value;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.alexandria.java.core.error.NotYetImplementedError;
import com.g2forge.habitat.metadata.v2.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.v2.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.v2.access.IMetadataRegistry.IFindContext;
import com.g2forge.habitat.metadata.v2.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.v2.value.subject.ElementSubject;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;
import com.g2forge.habitat.metadata.v2.value.subject.MergedSubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MetadataValueContext implements IMetadataValueContext {
	protected final IMetadataTypeContext typeContext;

	@Getter(AccessLevel.PROTECTED)
	protected final IMetadataRegistry registry;

	@Override
	public IMetadataAccessor find(ISubjectType subjectType, IPredicateType<?> predicateType) {
		return getRegistry().find(new IFindContext() {}, subjectType, predicateType);
	}

	@Override
	public ISubject merge(Collection<? extends ISubject> subjects) {
		return new MergedSubject(this, subjects);
	}

	@Override
	public ISubject of(AnnotatedElement element, Object value) {
		if ((element == null) && (value instanceof AnnotatedElement)) return of((AnnotatedElement) value, null);
		if ((value != null) || (element == null)) throw new NotYetImplementedError();

		return new ElementSubject(this, element);
	}
}