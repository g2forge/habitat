package com.g2forge.habitat.trace;

import java.lang.reflect.Executable;
import java.util.Set;
import java.util.stream.Stream;

import com.g2forge.alexandria.java.core.marker.Helpers;

import lombok.experimental.UtilityClass;

@UtilityClass
@Helpers
public class HTrace {
	protected static ThrowableStackTraceAnalyzer createSTA() {
		return new ThrowableStackTraceAnalyzer(new Throwable(), 2);
	}

	protected static Thread[] enumerateThreads() {
		Thread[] threads = new Thread[(int) (Thread.activeCount() * 1.5)];
		while (true) {
			final int count = Thread.enumerate(threads);
			if (count <= threads.length) break;
			threads = new Thread[(int) (count * 1.5)];
		}
		return threads;
	}

	public static Executable getCaller() {
		return createSTA().getCaller();
	}

	public static Executable getEntrypoint(Set<EntrypointFilter> filters) {
		return createSTA().getEntrypoint(filters);
	}

	/**
	 * Get the caller method relative to the method which calls this. {@code getCaller(0)} will return the method that calls this. {@code getCaller(1)} will
	 * return the method that called that. Negative offsets start from the bottom of the stack, so {@code getCaller(-1)} will return the main method.
	 * 
	 * @param offset The offset relative to the caller of this method.
	 * @return
	 */
	public static Executable getExecutable(int offset) {
		return createSTA().getExecutable(offset, 2);
	}

	public static Executable getMain() {
		return createSTA().getMain();
	}

	public static Thread getMainThread() {
		return Stream.of(enumerateThreads()).filter(t -> t.getId() == 1).findAny().get();
	}
}
