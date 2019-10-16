package com.g2forge.habitat.metadata.value.predicate;

import com.g2forge.alexandria.java.adt.tuple.ITuple1G_;
import com.g2forge.alexandria.java.type.IGenericTyped;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public interface IPredicate<T> extends IGenericTyped<T, IPredicateType<T>>, ITuple1G_<T> {
	public boolean isPresent();

	public ISubject getSubject();
}
