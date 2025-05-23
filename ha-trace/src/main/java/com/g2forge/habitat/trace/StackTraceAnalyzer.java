package com.g2forge.habitat.trace;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class StackTraceAnalyzer implements IStackTraceAnalyzer {
	protected final Object context;

	protected final Throwable throwable;

	@Getter(lazy = true)
	@EqualsAndHashCode.Exclude
	private final List<? extends ISmartStackTraceElement> elements = computeElements();

	public StackTraceAnalyzer() {
		this(new Throwable());
	}

	public StackTraceAnalyzer(Object context) {
		this(context, new Throwable());
	}

	public StackTraceAnalyzer(Throwable throwable) {
		this(throwable, throwable);
	}

	protected List<? extends ISmartStackTraceElement> computeElements() {
		final ClassLoader classLoader = getContextClassloader();
		return Stream.of(getThrowable().getStackTrace()).map(e -> new SmartStackTraceElement(e, classLoader)).collect(Collectors.toList());
	}

	@Override
	public Executable getCaller() {
		return getExecutable(0, 2);
	}

	protected ClassLoader getContextClassloader() {
		final ClassLoader retVal = getContext().getClass().getClassLoader();
		if (retVal == null) return Thread.currentThread().getContextClassLoader();
		return retVal;
	}

	@Override
	public Executable getEntrypoint(Set<EntrypointFilter> filters) {
		final List<? extends ISmartStackTraceElement> elements = this.getElements();
		final List<? extends ISmartStackTraceElement> limited = new ArrayList<>(elements.subList(2, elements.size()));
		Collections.reverse(limited);
		final Set<String> prefixes = filters.stream().flatMap(filter -> Stream.of(filter.getPrefixes())).collect(Collectors.toSet());
		final ISmartStackTraceElement element = limited.stream().filter(e -> {
			final String packageName = e.getDeclaringClass().getPackageName();
			return !prefixes.stream().filter(prefix -> packageName.startsWith(prefix)).findAny().isPresent();
		}).findFirst().orElse(null);
		return (element != null) ? element.getExecutable() : null;
	}

	protected Executable getExecutable(int offset, int invisible) {
		final StackTraceElement[] stackTrace = getThrowable().getStackTrace();

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

	@Override
	public Executable getMain() {
		return getExecutable(-1, 2);
	}
}
