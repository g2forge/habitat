package com.g2forge.habitat.trace;

import java.util.List;

public interface ISmartStackTrace {
	public List<? extends ISmartStackTraceElement> getElements();
}
