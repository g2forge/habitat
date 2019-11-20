package com.g2forge.habitat.metadata.value.implementations;

import java.util.Collection;

import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataAccessorFactory;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.IMetadataRegistry.IFindContext;
import com.g2forge.habitat.metadata.access.NoneMetadataAccessorFactory;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MetadataValueContext extends AMetadataSubjectFactory implements IMetadataValueContext {
	protected final IMetadataTypeContext typeContext;

	@Getter(AccessLevel.PROTECTED)
	protected final IMetadataRegistry registry;

	@Override
	public IMetadataAccessor find(ISubjectType subjectType, IPredicateType<?> predicateType) {
		return getRegistry().find(new IFindContext() {
			@Override
			public IMetadataAccessorFactory getDescendant() {
				return NoneMetadataAccessorFactory.create();
			}

			@Override
			public IMetadataAccessorFactory getTop() {
				return MetadataValueContext.this;
			}
		}, subjectType, predicateType);
	}

	@Override
	protected IMetadataValueContext getValueContext() {
		return this;
	}

	@Override
	public ISubject merge(Collection<? extends ISubject> subjects) {
		return new MergedSubject(this, subjects);
	}
}