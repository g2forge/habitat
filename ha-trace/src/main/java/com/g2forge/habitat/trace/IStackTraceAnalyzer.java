package com.g2forge.habitat.trace;

import java.util.List;
import java.util.Set;

import com.g2forge.habitat.trace.executable.IExecutable;

public interface IStackTraceAnalyzer {
	public IExecutable getCaller();

	public List<? extends ISmartStackTraceElement> getElements();

	public IExecutable getEntrypoint(Set<EntrypointFilter> filters);

	public IExecutable getMain();
}
