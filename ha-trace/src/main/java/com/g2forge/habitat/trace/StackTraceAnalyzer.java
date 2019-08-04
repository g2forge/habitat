package com.g2forge.habitat.trace;

import java.util.List;
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
	protected final Throwable throwable;

	@Getter(lazy = true)
	@EqualsAndHashCode.Exclude
	private final List<? extends ISmartStackTraceElement> elements = computeElements();

	protected List<? extends ISmartStackTraceElement> computeElements() {
		final ClassLoader classLoader = getThrowable().getClass().getClassLoader();
		return Stream.of(getThrowable().getStackTrace()).map(e -> new SmartStackTraceElement(e, classLoader)).collect(Collectors.toList());
	}

}
