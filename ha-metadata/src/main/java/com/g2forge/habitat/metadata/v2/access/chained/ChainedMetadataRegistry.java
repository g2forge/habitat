package com.g2forge.habitat.metadata.v2.access.chained;

import java.util.ArrayList;
import java.util.List;

import com.g2forge.alexandria.java.core.error.HError;
import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.habitat.metadata.v2.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.v2.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.v2.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.type.subject.ISubjectType;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ChainedMetadataRegistry implements IMetadataRegistry {
	@Singular
	protected final List<? extends IMetadataRegistry> registries;

	public ChainedMetadataRegistry(IMetadataRegistry... registries) {
		this(HCollection.asList(registries));
	}

	@Override
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		final IFindContext childContext = new IFindContext() {};
		final List<NoAccessorFoundException> exceptions = new ArrayList<>();
		for (IMetadataRegistry registry : getRegistries()) {
			try {
				return registry.find(childContext, subjectType, predicateType);
			} catch (NoAccessorFoundException exception) {
				exceptions.add(exception);
			}
		}
		throw HError.addSuppressed(new NoAccessorFoundException("None of the chained registries found an appropriate accessor!"), exceptions);
	}
}
