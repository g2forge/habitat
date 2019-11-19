package com.g2forge.habitat.metadata.access.chained;

import java.util.ArrayList;
import java.util.List;

import com.g2forge.alexandria.java.core.error.HError;
import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataAccessorFactory;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ChainedMetadataRegistry implements IMetadataRegistry {
	@RequiredArgsConstructor
	protected static class SubListFindContext implements IFindContext {
		protected final IFindContext context;

		protected final List<? extends IMetadataRegistry> registries;

		protected final int i;

		@Override
		public IMetadataAccessorFactory getDescendant() {
			return (ISubjectType subjectType, IPredicateType<?> predicateType) -> {
				if (i >= registries.size()) throw new NoAccessorFoundException();
				return find(new SubListFindContext(context, registries, i + 1), subjectType, predicateType, registries.subList(i + 1, registries.size()));
			};
		}

		@Override
		public IMetadataAccessorFactory getTop() {
			return context.getTop();
		}
	}

	protected static IMetadataAccessor find(final IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType, final List<? extends IMetadataRegistry> registries) {
		final List<NoAccessorFoundException> exceptions = new ArrayList<>();
		for (int i = 0; i < registries.size(); i++) {
			try {
				return registries.get(i).find(new SubListFindContext(context, registries, i), subjectType, predicateType);
			} catch (NoAccessorFoundException exception) {
				exceptions.add(exception);
			}
		}
		throw HError.addSuppressed(new NoAccessorFoundException(String.format("None of the chained registries found an appropriate accessor for subject type %1$s and predicate type %2$s!", subjectType, predicateType)), exceptions);
	}

	@Singular
	protected final List<? extends IMetadataRegistry> registries;

	public ChainedMetadataRegistry(IMetadataRegistry... registries) {
		this(HCollection.asList(registries));
	}

	@Override
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		return find(context, subjectType, predicateType, getRegistries());
	}
}
