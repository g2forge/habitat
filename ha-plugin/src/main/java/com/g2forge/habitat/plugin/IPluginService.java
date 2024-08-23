package com.g2forge.habitat.plugin;

import com.g2forge.alexandria.adt.collection.ICollection;

public interface IPluginService {
	public ICollection<PluginDescriptor<?, ?>> load();
}
