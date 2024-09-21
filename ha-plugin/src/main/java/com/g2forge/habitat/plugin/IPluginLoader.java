package com.g2forge.habitat.plugin;

import com.g2forge.alexandria.collection.ICollection;

public interface IPluginLoader<P> {
	public ICollection<? extends P> load();
}
