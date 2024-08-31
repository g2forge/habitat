package com.g2forge.habitat.plugin;

import com.g2forge.alexandria.adt.collection.CollectionCollection;
import com.g2forge.alexandria.adt.collection.ICollection;
import com.g2forge.alexandria.annotations.service.Service;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service(IPluginService.class)
public class SingleDependentPluginService implements IPluginService {
	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	public static class PluginA {}

	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	public static class PluginB {
		protected final PluginA a;
	}

	@Override
	public ICollection<PluginDescriptor<?, ?>> load() {
		final PluginDescriptor.PluginDescriptorBuilder<PluginB, PluginB.PluginBBuilder> b = PluginDescriptor.builder();
		b.type(PluginB.class).constructor(PluginB.PluginBBuilder::new);
		b.dependency_(PluginA.class, PluginB.PluginBBuilder::a);
		b.builder(PluginB.PluginBBuilder::build);

		return new CollectionCollection<>(PluginDescriptor.create(PluginA.class, PluginA::new), b.build());
	}
}