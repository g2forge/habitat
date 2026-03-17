package com.g2forge.habitat.inject;

import com.g2forge.alexandria.java.type.ref.ITypeRef;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class InjectedValueDescriptor<T> {
	protected final String id;

	protected final ITypeRef<T> type;

	protected final T fallback;

	public InjectedValueDescriptor(Class<?> owner, String name, ITypeRef<T> type, T fallback) {
		this(owner.getName() + "." + name, type, fallback);
	}

	public InjectedValueDescriptor(Class<T> type, T fallback) {
		this(type.getName(), ITypeRef.of(type), fallback);
	}
}