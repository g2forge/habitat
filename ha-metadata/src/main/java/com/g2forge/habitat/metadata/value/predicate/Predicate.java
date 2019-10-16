package com.g2forge.habitat.metadata.value.predicate;

import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.value.subject.ISubject;

public class Predicate<T> implements IPredicate<T> {
	protected final IPredicateType<T> type;
	

	public Predicate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IPredicateType<T> getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get0() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ISubject<?> getSubject() {
		// TODO Auto-generated method stub
		return null;
	}

}
