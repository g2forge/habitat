package com.g2forge.habitat.trace;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Stream;

import com.g2forge.alexandria.java.core.helpers.HStream;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class SmartStackTraceElement implements ISmartStackTraceElement {
	@Delegate(types = IStackTraceElement.class)
	protected final StackTraceElement element;

	@Getter(lazy = true)
	private final Class<?> declaringClass = computeDeclaringClass();

	@Getter(lazy = true)
	private final Method method = computeMethod();

	protected Class<?> computeDeclaringClass() {
		final String className = getClassName();
		try {
			return getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("Could not load %1$s, perhaps we don't support whatever classloaders you're current using yet", className), e);
		}
	}

	protected Method computeMethod() {
		// Use the line number to look up the method descriptor, that way we can handle overloaded methods correctly
		final Map<Integer, String> lineMap;
		try (final InputStream classStream = getClassLoader().getResourceAsStream(getClassName().replace('.', '/') + ".class")) {
			lineMap = HTrace.getLineMap(classStream, element.getMethodName());
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		final String descriptor = lineMap.get(element.getLineNumber());

		// Find the method with the correct name and descriptor
		return HStream.findOne(Stream.of(getDeclaringClass().getDeclaredMethods()).filter(m -> element.getMethodName().equals(m.getName())).filter(m -> HTrace.getDescriptor(m).equals(descriptor)));
	}

	/**
	 * Get the class loader to use. This should be the class loader which can load the class mentioned in the stack trace.
	 * 
	 * @return The class loader to use for reflection.
	 */
	protected ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
