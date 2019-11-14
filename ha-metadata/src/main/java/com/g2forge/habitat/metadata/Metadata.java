package com.g2forge.habitat.metadata;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.g2forge.alexandria.java.function.IFunction1;
import com.g2forge.alexandria.java.function.builder.IBuilder;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.annotation.AnnotationMetadataRegistry;
import com.g2forge.habitat.metadata.access.caching.CachingMetadataRegistry;
import com.g2forge.habitat.metadata.access.chained.ChainedMetadataRegistry;
import com.g2forge.habitat.metadata.access.computed.MixinMetadataRegistry;
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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

@Builder(toBuilder = true)
public class Metadata implements IMetadata {
	public static class MetadataBuilder implements IBuilder<IMetadata> {
		public MetadataBuilder mixins(IFunction1<? super MixinMetadataRegistry.MixinMetadataRegistryBuilder, ? extends MixinMetadataRegistry> function) {
			mixinRegistry(function.apply(MixinMetadataRegistry.builder()));
			return this;
		}
	}

	@Getter(lazy = true)
	private static final IMetadata standard = Metadata.builder().build();

	@Default
	@Getter(AccessLevel.PROTECTED)
	protected final MixinMetadataRegistry mixinRegistry = null;

	@Getter(lazy = true)
	private final IMetadataTypeContext typeContext = computeTypeContext();

	@Getter(lazy = true)
	private final IMetadataValueContext context = computeContext();

	protected IMetadataValueContext computeContext() {
		final List<IMetadataRegistry> registries = computeRegistries();
		registries.removeIf(Objects::isNull);
		final MetadataTypeContext typeContext = computeTypeContext();
		final CachingMetadataRegistry registry = new CachingMetadataRegistry(new ChainedMetadataRegistry(registries));
		return new MetadataValueContext(typeContext, registry);
	}

	protected List<IMetadataRegistry> computeRegistries() {
		final List<IMetadataRegistry> registries = new ArrayList<>();
		registries.add(getMixinRegistry());
		registries.add(new MergedMetadataRegistry());
		registries.add(new IndirectMetadataRegistry());
		registries.add(new ValueMetadataRegistry());
		registries.add(new AnnotationMetadataRegistry());
		return registries;
	}

	protected MetadataTypeContext computeTypeContext() {
		return new MetadataTypeContext(new IMetadataValueContext() {
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
	}
}