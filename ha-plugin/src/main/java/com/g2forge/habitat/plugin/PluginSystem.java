package com.g2forge.habitat.plugin;

import java.util.ArrayList;
import java.util.List;

import com.g2forge.alexandria.adt.graph.v2.DiGraphBuilder;
import com.g2forge.alexandria.adt.graph.v2.member.ASingleGraphMember;
import com.g2forge.alexandria.annotations.note.Note;
import com.g2forge.alexandria.annotations.note.NoteType;
import com.g2forge.alexandria.collection.DStreamCollection;
import com.g2forge.alexandria.collection.ICollection;
import com.g2forge.alexandria.java.function.ISupplier;
import com.g2forge.alexandria.java.type.ref.ITypeRef;
import com.g2forge.alexandria.service.BasicServiceLoader;
import com.g2forge.alexandria.service.IServiceLoader;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PluginSystem implements IPluginSystem {
	protected static class Edge extends ASingleGraphMember {}

	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	@EqualsAndHashCode(callSuper = false)
	protected static class Vertex extends ASingleGraphMember {
		protected final PluginDescriptor<?, ?> descriptor;
	}

	@Getter(lazy = true)
	private static final IPluginSystem standard = new PluginSystem();

	@Note(type = NoteType.TODO, issue = "G2-1628")
	protected <P, B> P instantiate(PluginDescriptor<P, B> descriptor) {
		final ISupplier<B> constructor = descriptor.getConstructor();
		final B builder = constructor.get();
		return descriptor.getBuilder().apply(builder);
	}

	@Override
	@Note(type = NoteType.TODO, issue = "G2-1628")
	public <P> IPluginLoader<P> load(Class<P> type) {
		return new IPluginLoader<P>() {
			@Override
			public ICollection<? extends P> load() {
				// Load the descriptors
				final List<PluginDescriptor<?, ?>> descriptors = loadDescriptors();
				// TODO: Create a dependency graph, and extract an instantiation plan
				final DiGraphBuilder<Vertex, Edge> builder = new DiGraphBuilder<Vertex, Edge>();
				descriptors.stream().map(Vertex::new);
				//builder.vertices(vertices)
				// Instantiate the plugins
				final List<Object> plugins = new ArrayList<>();
				for (PluginDescriptor<?, ?> descriptor : descriptors) {
					plugins.add(instantiate(descriptor));
				}

				return (DStreamCollection<? extends P>) (() -> plugins.stream().flatMap(ITypeRef.of(type)::castIfInstance));
			}
		};
	}

	private List<PluginDescriptor<?, ?>> loadDescriptors() {
		final IServiceLoader<IPluginService> loader = new BasicServiceLoader<>(IPluginService.class);
		final List<PluginDescriptor<?, ?>> descriptors = new ArrayList<>();
		for (IPluginService pluginService : loader.load()) {
			for (PluginDescriptor<?, ?> descriptor : pluginService.load()) {
				validateDescriptor(descriptor);
				descriptors.add(descriptor);
			}
		}
		return descriptors;
	}

	protected void validateDescriptor(PluginDescriptor<?, ?> descriptor) {
		if (descriptor.getType() == null) throw new NullPointerException();
		if (descriptor.getBuilder() == null) throw new NullPointerException();
		if (descriptor.getConstructor() == null) throw new NullPointerException();
	}
}
