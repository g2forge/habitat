package com.g2forge.habitat.trace;

import java.util.List;

public interface IStackTraceAnalyzer {
	public List<? extends ISmartStackTraceElement> getElements();
}
