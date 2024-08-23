package com.g2forge.habitat.plugin;

public interface IPluginSystem {
	public <P> IPluginLoader<P> load(Class<P> type);
}
