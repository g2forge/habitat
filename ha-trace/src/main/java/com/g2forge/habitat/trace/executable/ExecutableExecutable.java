package com.g2forge.habitat.trace.executable;

import java.lang.reflect.Executable;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ExecutableExecutable implements IExecutable {
	protected final Executable executable;

	@Override
	public Class<?> getDeclaringClass() {
		return getExecutable().getDeclaringClass();
	}

	@Override
	public int getModifiers() {
		return getExecutable().getModifiers();
	}

	@Override
	public String getName() {
		return getExecutable().getName();
	}

	@Override
	public boolean isSynthetic() {
		return getExecutable().isSynthetic();
	}
}
