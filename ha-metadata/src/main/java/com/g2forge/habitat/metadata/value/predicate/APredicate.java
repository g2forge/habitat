package com.g2forge.habitat.metadata.value.predicate;

import com.g2forge.alexandria.java.fluent.optional.AOptional;
import com.g2forge.alexandria.java.fluent.optional.NullableOptional;

public abstract class APredicate<T> extends AOptional<T> implements IPredicate<T> {
	@Override
	protected <U> AOptional<U> create() {
		return NullableOptional.empty();
	}

	@Override
	protected <U> AOptional<U> create(U value) {
		return NullableOptional.of(value);
	}
}
