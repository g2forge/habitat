package com.g2forge.habitat.plugin;

import java.util.ArrayList;
import java.util.List;

import com.g2forge.alexandria.adt.collection.DStreamCollection;
import com.g2forge.alexandria.adt.collection.ICollection;
import com.g2forge.alexandria.annotations.note.Note;
import com.g2forge.alexandria.annotations.note.NoteType;
import com.g2forge.alexandria.java.function.ISupplier;
import com.g2forge.alexandria.java.type.ref.ITypeRef;
import com.g2forge.alexandria.service.BasicServiceLoader;
import com.g2forge.alexandria.service.IServiceLoader;

import lombok.Getter;

public class PluginSystem implements IPluginSystem {
	@Getter(lazy = true)
	private static final IPluginSystem standard = new PluginSystem();

	@Note(type = NoteType.TODO, issue = "G2-1628")
	protected <P, B> P instantiate(PluginDescriptor<P, B> descriptor) {
		final ISupplier<B> constructor = descriptor.getConstructor();
		final B builder = constructor.get();
		return descriptor.getBuilder().apply(builder);
	}

	@Override
	public <P> IPluginLoader<P> load(Class<P> type) {
		return new IPluginLoader<P>() {
			@Override
			public ICollection<? extends P> load() {
				final IServiceLoader<IPluginService> loader = new BasicServiceLoader<>(IPluginService.class);
				final List<Object> plugins = new ArrayList<>();
				for (IPluginService pluginService : loader.load()) {
					for (PluginDescriptor<?, ?> descriptor : pluginService.load()) {
						plugins.add(instantiate(descriptor));
					}
				}
				return (DStreamCollection<? extends P>) (() -> plugins.stream().flatMap(ITypeRef.of(type)::castIfInstance));
			}
		};
	}
}
