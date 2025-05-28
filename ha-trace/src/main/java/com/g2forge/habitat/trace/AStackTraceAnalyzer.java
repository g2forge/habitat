package com.g2forge.habitat.trace;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;
import lombok.Getter;

public abstract class AStackTraceAnalyzer implements IStackTraceAnalyzer {
	@Getter(lazy = true)
	@EqualsAndHashCode.Exclude
	private final List<? extends ISmartStackTraceElement> elements = computeElements();

	protected List<? extends ISmartStackTraceElement> computeElements() {
		final ClassLoader classLoader = getContextClassloader();
		return Stream.of(getStackTrace()).map(e -> new SmartStackTraceElement(e, classLoader)).collect(Collectors.toList());
	}

	@Override
	public Executable getCaller() {
		return getExecutable(0, getInvisibles());
	}

	protected abstract ClassLoader getContextClassloader();
	
	@Override
	public Executable getEntrypoint(Set<EntrypointFilter> filters) {
		final List<? extends ISmartStackTraceElement> elements = this.getElements();
		final List<? extends ISmartStackTraceElement> limited = new ArrayList<>(elements.subList(getInvisibles(), elements.size()));
		Collections.reverse(limited);
		final Set<String> prefixes = filters.stream().flatMap(filter -> Stream.of(filter.getPrefixes())).collect(Collectors.toSet());
		final ISmartStackTraceElement element = limited.stream().filter(e -> {
			final String packageName = e.getDeclaringClass().getName();
			return !prefixes.stream().filter(prefix -> packageName.startsWith(prefix)).findAny().isPresent();
		}).findFirst().orElse(null);
		return (element != null) ? element.getExecutable() : null;
	}

	protected Executable getExecutable(int offset, int invisible) {
		final StackTraceElement[] stackTrace = getStackTrace();

		final int actual, pretendDepth = stackTrace.length - invisible;
		if (offset >= 0) {
			if (offset >= pretendDepth) throw new IllegalArgumentException();
			actual = offset + invisible;
		} else {
			if (offset < -pretendDepth) throw new IllegalArgumentException();
			actual = stackTrace.length + offset;
		}

		return new SmartStackTraceElement(stackTrace[actual], getContextClassloader()).getExecutable();
	}

	protected abstract int getInvisibles();

	@Override
	public Executable getMain() {
		return getExecutable(-1, getInvisibles());
	}

	protected abstract StackTraceElement[] getStackTrace();
}
