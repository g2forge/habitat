package com.g2forge.habitat.plugin;

import com.g2forge.alexandria.adt.collection.CollectionCollection;
import com.g2forge.alexandria.adt.collection.ICollection;
import com.g2forge.alexandria.annotations.service.Service;
import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.java.function.IFunction1;
import com.g2forge.habitat.plugin.TestPluginSystem.Plugin;

@Service(IPluginService.class)
public class PluginService implements IPluginService {
	@Override
	public ICollection<PluginDescriptor<?, ?>> load() {
		final PluginDescriptor.PluginDescriptorBuilder<Plugin, Plugin> retVal = PluginDescriptor.builder();
		retVal.constructor(Plugin::create);
		retVal.builder(IFunction1.identity());
		return new CollectionCollection<>(HCollection.asList(retVal.build()));
	}
}