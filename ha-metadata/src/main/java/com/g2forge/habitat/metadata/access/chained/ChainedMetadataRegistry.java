package com.g2forge.habitat.metadata.access.chained;

import java.util.ArrayList;
import java.util.List;

import com.g2forge.alexandria.java.core.error.HError;
import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

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
	public IMetadataAccessor find(ISubject subject, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		final List<NoAccessorFoundException> exceptions = new ArrayList<>();
		for (int i = 0; i < registries.size(); i++) {
			try {
				return registries.get(i).find(subject, predicateType);
			} catch (NoAccessorFoundException exception) {
				exceptions.add(exception);
			}
		}
		throw HError.withSuppressed(new NoAccessorFoundException(String.format("None of the chained registries found an appropriate accessor for subject type %1$s and predicate type %2$s!", subject, predicateType)), exceptions);
	}
}
