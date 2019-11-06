package com.g2forge.habitat.metadata;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.annotation.AnnotationMetadataRegistry;
import com.g2forge.habitat.metadata.access.caching.CachingMetadataRegistry;
import com.g2forge.habitat.metadata.access.chained.ChainedMetadataRegistry;
import com.g2forge.habitat.metadata.access.indirect.IndirectMetadataRegistry;
import com.g2forge.habitat.metadata.access.merged.MergedMetadataRegistry;
import com.g2forge.habitat.metadata.access.value.ValueMetadataRegistry;
import com.g2forge.habitat.metadata.type.IMetadataTypeContext;
import com.g2forge.habitat.metadata.type.implementations.MetadataTypeContext;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.value.implementations.MetadataValueContext;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.Getter;

public class Metadata implements IMetadata {
	@Getter(lazy = true)
	private static final IMetadata standard = new Metadata();

	@Getter(lazy = true)
	private final IMetadataValueContext context = computeContext();

	protected IMetadataValueContext computeContext() {
		final MetadataTypeContext typeContext = new MetadataTypeContext(new IMetadataValueContext() {
			@Override
			public IMetadataAccessor find(ISubjectType subjectType, IPredicateType<?> predicateType) {
				return getContext().find(subjectType, predicateType);
			}

			@Override
			public IMetadataTypeContext getTypeContext() {
				return getContext().getTypeContext();
			}

			@Override
			public ISubject merge(Collection<? extends ISubject> subjects) {
				return getContext().merge(subjects);
			}

			@Override
			public ISubject of(AnnotatedElement element, Object value) {
				return getContext().of(element, value);
			}
		});
		final CachingMetadataRegistry registry = new CachingMetadataRegistry(new ChainedMetadataRegistry(new MergedMetadataRegistry(), new IndirectMetadataRegistry(), new ValueMetadataRegistry(), new AnnotationMetadataRegistry()));
		return new MetadataValueContext(typeContext, registry);
	}
}