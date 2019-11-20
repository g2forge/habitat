package com.g2forge.habitat.metadata.value.implementations;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;
import com.g2forge.habitat.metadata.value.subject.SubjectDescriptor;

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
	public IMetadataAccessor find(ISubject subject, IPredicateType<?> predicateType) {
		return getRegistry().find(subject, predicateType);
	}

	@Override
	public ISubject merge(Collection<? extends ISubject> subjects) {
		return new MergedSubject(this, subjects);
	}

	@Override
	public ISubject of(AnnotatedElement element, Object value) {
		final SubjectDescriptor descriptor = new SubjectDescriptor(element, value).reduce();
		if (descriptor.getValue() == null) return new ElementSubject(this, descriptor.getElement());
		return new ElementValueSubject(this, descriptor.getElement(), descriptor.getValue());
	}
}