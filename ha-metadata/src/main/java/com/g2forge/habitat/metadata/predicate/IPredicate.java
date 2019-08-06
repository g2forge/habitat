package com.g2forge.habitat.metadata.predicate;

import com.g2forge.alexandria.java.adt.tuple.ITuple1G_;
import com.g2forge.alexandria.java.type.IGenericTyped;
import com.g2forge.habitat.metadata.subject.ISubject;

public interface IPredicate<T> extends IGenericTyped<T, IPredicateType<T>>, ITuple1G_<T> {
	public boolean isPresent();

	public ISubject getSubject();
}
