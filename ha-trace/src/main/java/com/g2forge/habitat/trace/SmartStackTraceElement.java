package com.g2forge.habitat.trace;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import com.g2forge.alexandria.java.core.helpers.HStream;

import lombok.AccessLevel;
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

	@Getter(AccessLevel.PROTECTED)
	protected final ClassLoader classLoader;

	@Getter(lazy = true)
	private final Class<?> declaringClass = computeDeclaringClass();

	@Getter(lazy = true)
	private final Executable executable = computeExecutable();

	@Getter(lazy = true)
	private final Field initialized = computeInitialized();

	public SmartStackTraceElement(StackTraceElement element) {
		this(element, Thread.currentThread().getContextClassLoader());
	}

	protected Class<?> computeDeclaringClass() {
		final String className = getClassName();
		try {
			return getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("Could not load %1$s, perhaps we don't support whatever classloaders you're current using yet", className), e);
		}
	}

	protected Executable computeExecutable() {
		// Use the line number to look up the method descriptor, that way we can handle overloaded methods correctly
		final Map<Integer, String> lineMap;
		try (final InputStream classStream = getClassLoader().getResourceAsStream(getClassName().replace('.', '/') + ".class")) {
			lineMap = HTrace.getLine2MethodMap(classStream, element.getMethodName());
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		final String descriptor = lineMap.get(element.getLineNumber());

		// Find the method with the correct name and descriptor
		if (HTrace.INITIALIZER.equals(element.getMethodName())) return HStream.findOne(Stream.of(getDeclaringClass().getDeclaredConstructors()).filter(c -> HTrace.getDescriptor(c).equals(descriptor)));
		else return HStream.findOne(Stream.of(getDeclaringClass().getDeclaredMethods()).filter(m -> element.getMethodName().equals(m.getName())).filter(m -> HTrace.getDescriptor(m).equals(descriptor)));
	}

	protected Field computeInitialized() {
		if (!HTrace.INITIALIZER.equals(element.getMethodName())) return null;

		final Map<Integer, String> lineMap;
		final String className = getClassName().replace('.', '/');
		try (final InputStream classStream = getClassLoader().getResourceAsStream(className + ".class")) {
			lineMap = HTrace.getLine2FieldMap(classStream, className);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		final String fieldName = lineMap.get(element.getLineNumber());
		if (fieldName == null) return null;

		return HStream.findOne(Stream.of(getDeclaringClass().getDeclaredFields()).filter(f -> fieldName.equals(f.getName())));
	}
}
