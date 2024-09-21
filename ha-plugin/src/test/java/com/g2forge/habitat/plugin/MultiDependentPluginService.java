package com.g2forge.habitat.plugin;

import java.util.List;

import com.g2forge.alexandria.annotations.service.Service;
import com.g2forge.alexandria.collection.CollectionCollection;
import com.g2forge.alexandria.collection.ICollection;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

@Service(IPluginService.class)
public class MultiDependentPluginService implements IPluginService {
	public static interface IPluginA {}

	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	public static class PluginA1 implements IPluginA {}

	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	public static class PluginA2 implements IPluginA {}

	@Data
	@Builder(toBuilder = true)
	@RequiredArgsConstructor
	public static class PluginB {
		@Singular
		protected final List<IPluginA> as;
	}

	@Override
	public ICollection<PluginDescriptor<?, ?>> load() {
		final PluginDescriptor.PluginDescriptorBuilder<PluginB, PluginB.PluginBBuilder> b = PluginDescriptor.builder();
		b.type(PluginB.class).constructor(PluginB.PluginBBuilder::new);
		b.dependency_(IPluginA.class, PluginB.PluginBBuilder::a);
		b.builder(PluginB.PluginBBuilder::build);

		return new CollectionCollection<>(PluginDescriptor.create(PluginA1.class, PluginA1::new), PluginDescriptor.create(PluginA2.class, PluginA2::new), b.build());
	}
}