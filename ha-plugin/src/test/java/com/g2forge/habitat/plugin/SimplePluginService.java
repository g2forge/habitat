package com.g2forge.habitat.plugin;

import com.g2forge.alexandria.annotations.service.Service;
import com.g2forge.alexandria.collection.ICollection;
import com.g2forge.alexandria.collection.SingleCollection;

@Service(IPluginService.class)
public class SimplePluginService implements IPluginService {
	public static class Plugin {
		protected static final Plugin INSTANCE = new Plugin();

		public static Plugin create() {
			return INSTANCE;
		}

		private Plugin() {}
	}

	@Override
	public ICollection<PluginDescriptor<?, ?>> load() {
		return new SingleCollection<>(PluginDescriptor.create(Plugin.class, Plugin::create));
	}
}