package com.g2forge.habitat.plugin;

import java.util.HashSet;

import org.junit.Ignore;
import org.junit.Test;

import com.g2forge.alexandria.annotations.note.Note;
import com.g2forge.alexandria.annotations.note.NoteType;
import com.g2forge.alexandria.java.core.helpers.HCollection;
import com.g2forge.alexandria.test.HAssert;

public class TestPluginSystem {
	@Test
	@Ignore
	@Note(type = NoteType.TODO, issue = "G2-1628")
	public void multiDependent() {
		final MultiDependentPluginService.PluginB b = HCollection.getOne(PluginSystem.getStandard().load(MultiDependentPluginService.PluginB.class).load().toCollection());
		HAssert.assertEquals(HCollection.asSet(new MultiDependentPluginService.PluginA1(), new MultiDependentPluginService.PluginA2()), new HashSet<>(b.getAs()));
	}

	@Test
	public void simple() {
		HAssert.assertEquals(HCollection.asList(SimplePluginService.Plugin.create()), PluginSystem.getStandard().load(SimplePluginService.Plugin.class).load().toCollection());
	}

	@Test
	@Ignore
	@Note(type = NoteType.TODO, issue = "G2-1628")
	public void singleDependent() {
		final SingleDependentPluginService.PluginB b = HCollection.getOne(PluginSystem.getStandard().load(SingleDependentPluginService.PluginB.class).load().toCollection());
		HAssert.assertInstanceOf(SingleDependentPluginService.PluginA.class, b.getA());
	}
}
