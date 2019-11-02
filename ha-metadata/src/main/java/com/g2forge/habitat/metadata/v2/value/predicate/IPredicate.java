package com.g2forge.habitat.metadata.v2.value.predicate;

import com.g2forge.alexandria.java.adt.tuple.ITuple1G_;
import com.g2forge.alexandria.java.type.IGenericTyped;
import com.g2forge.habitat.metadata.v2.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.v2.value.IMetadataValue;
import com.g2forge.habitat.metadata.v2.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.v2.value.subject.ISubject;

public interface IPredicate<T> extends IGenericTyped<T, IPredicateType<T>>, ITuple1G_<T>, IMetadataValue {
	@Override
	public default IMetadataValueContext getContext() {
		return getSubject().getContext();
	}

	public ISubject getSubject();

	public boolean isPresent();
}
