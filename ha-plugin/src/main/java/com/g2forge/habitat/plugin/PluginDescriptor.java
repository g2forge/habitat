package com.g2forge.habitat.plugin;

import java.util.List;

import com.g2forge.alexandria.java.function.IConsumer2;
import com.g2forge.alexandria.java.function.IFunction1;
import com.g2forge.alexandria.java.function.ISupplier;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class PluginDescriptor<P, B> {
	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	public static class Dependency<T, U> {
		protected final Class<U> type;

		protected final IConsumer2<? super T, ? super U> callback;
	}

	@Singular
	protected final List<Dependency<B, ?>> dependencies;

	protected final ISupplier<B> constructor;

	protected final IFunction1<? super B, ? extends P> builder;
}
