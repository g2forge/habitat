package com.g2forge.habitat.metadata.access.computed;

import java.util.LinkedHashMap;
import java.util.Map;

import com.g2forge.alexandria.java.function.IPredicate2;
import com.g2forge.habitat.metadata.access.IMetadataAccessor;
import com.g2forge.habitat.metadata.access.IMetadataRegistry;
import com.g2forge.habitat.metadata.access.ITypedMetadataAccessor;
import com.g2forge.habitat.metadata.access.NoAccessorFoundException;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

import lombok.AccessLevel;
import lombok.Getter;

public class MixinMetadataRegistry implements IMetadataRegistry {
	@Getter(AccessLevel.PROTECTED)
	protected final Map<IPredicate2<? super ISubjectType, ? super IPredicateType<?>>, IMetadataAccessor> accessors = new LinkedHashMap<>();

	public <T> MixinMetadataRegistry add(ITypedMetadataAccessor<T, ? extends ISubject, ? extends IPredicateType<T>> accessor) {
		getAccessors().put(accessor::isApplicable, accessor);
		return this;
	}

	@Override
	public IMetadataAccessor find(IFindContext context, ISubjectType subjectType, IPredicateType<?> predicateType) throws NoAccessorFoundException {
		for (Map.Entry<IPredicate2<? super ISubjectType, ? super IPredicateType<?>>, IMetadataAccessor> entry : getAccessors().entrySet()) {
			if (entry.getKey().test(subjectType, predicateType)) return entry.getValue();
		}
		throw new NoAccessorFoundException("No matching accessors found");
	}
}
