package com.g2forge.habitat.metadata.access.caching;

import com.g2forge.alexandria.adt.associative.cache.Cache;
import com.g2forge.alexandria.adt.associative.cache.LRUCacheEvictionPolicy;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
public class CachingMetadataRegistry implements IMetadataRegistry {
	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	protected static class Key {
		@EqualsAndHashCode.Exclude
		@ToString.Exclude
		protected final IFindContext context;

		protected final ISubjectType subjectType;

		protected final IPredicateType<?> predicateType;
	}

	protected final IMetadataRegistry registry;

	@Getter(AccessLevel.PROTECTED)
	protected final Cache<Key, IMetadataAccessor> cache = new Cache<>(key -> getRegistry().find(key.getContext(), key.getSubjectType(), key.getPredicateType()), new LRUCacheEvictionPolicy<>(100));

	@Override
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		return getCache().apply(new Key(context, subjectType, predicateType));
	}
}
