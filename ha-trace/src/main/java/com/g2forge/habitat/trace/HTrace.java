package com.g2forge.habitat.trace;

import java.lang.reflect.Executable;
import java.util.Set;

import com.g2forge.alexandria.java.core.marker.Helpers;

import lombok.experimental.UtilityClass;

@UtilityClass
@Helpers
public class HTrace {
	public static Executable getCaller() {
		return new StackTraceAnalyzer().getCaller();
	}

	public static Executable getEntrypoint(Set<EntrypointFilter> filters) {
		return new StackTraceAnalyzer().getEntrypoint(filters);
	}

	/**
	 * Get the caller method relative to the method which calls this. {@code getCaller(0)} will return the method that calls this. {@code getCaller(1)} will
	 * return the method that called that. Negative offsets start from the bottom of the stack, so {@code getCaller(-1)} will return the main method.
	 * 
	 * @param offset The offset relative to the caller of this method.
	 * @return
	 */
	public static Executable getExecutable(int offset) {
		return new StackTraceAnalyzer().getExecutable(offset, 2);
	}

	public static Executable getMain() {
		return new StackTraceAnalyzer().getMain();
	}
}
