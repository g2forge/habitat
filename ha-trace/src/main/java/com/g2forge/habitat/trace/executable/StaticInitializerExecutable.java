package com.g2forge.habitat.trace.executable;

import java.lang.reflect.Executable;
import java.lang.reflect.Modifier;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class StaticInitializerExecutable implements IExecutable {
	public static final String NAME = "<clinit>";

	protected final Class<?> declaringClass;

	@Override
	public Executable getExecutable() {
		return null;
	}

	@Override
	public int getModifiers() {
		return Modifier.STATIC;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isSynthetic() {
		return false;
	}
}
