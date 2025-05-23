package com.g2forge.habitat.trace;

import java.lang.reflect.Executable;
import java.util.List;
import java.util.Set;

public interface IStackTraceAnalyzer {
	public Executable getCaller();

	public List<? extends ISmartStackTraceElement> getElements();

	public Executable getEntrypoint(Set<EntrypointFilter> filters);

	public Executable getMain();
}
