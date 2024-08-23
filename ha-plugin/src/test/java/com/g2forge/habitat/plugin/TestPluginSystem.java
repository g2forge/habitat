package com.g2forge.habitat.plugin;

import org.junit.Test;

import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.test.HAssert;

public class TestPluginSystem {
	public static class Plugin {
		protected static final Plugin INSTANCE = new Plugin();

		public static Plugin create() {
			return INSTANCE;
		}

		private Plugin() {}
	}

	@Test
	public void test() {
		HAssert.assertEquals(HCollection.asList(Plugin.create()), PluginSystem.getStandard().load(Plugin.class).load().toCollection());
	}
}
