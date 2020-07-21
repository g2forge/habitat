package com.g2forge.habitat.metadata.access.caching;

import com.g2forge.alexandria.adt.associative.cache.Cache;
import com.g2forge.alexandria.adt.associative.cache.LRUCacheEvictionPolicy;
import com.g2forge.alexandria.java.function.IFunction1;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CachingMetadataRegistry implements IMetadataRegistry {
	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	protected static class Key {
		protected final ISubject subject;

		protected final IPredicateType<?> predicateType;
	}

	protected final IMetadataRegistry registry;

	@Getter(AccessLevel.PROTECTED)
	protected final IFunction1<Key, IMetadataAccessor> cache = Cache.<Key, IMetadataAccessor>builder().function(key -> getRegistry().find(key.getSubject(), key.getPredicateType())).policy(new LRUCacheEvictionPolicy<>(100)).build().sync(new Object());

	@Override
	public IMetadataAccessor find(ISubject subject, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		return getCache().apply(new Key(subject, predicateType));
	}
}
