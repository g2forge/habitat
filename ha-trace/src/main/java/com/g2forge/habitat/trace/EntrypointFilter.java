package com.g2forge.habitat.trace;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntrypointFilter {
	JAVA(new String[] { "java.", "javax.", "sun.", "sunw." }, false),
	IBM(new String[] { "com.ibm." }, false),
	SUN(new String[] { "com.sun." }, false),
	JROCKIT(new String[] { "jrockit." }, false),
	OMG(new String[] { "org.omg." }, false),
	ECLIPSE(new String[] { "org.eclipse.jdt.launching.internal." }, false),
	JDK(new String[] { "jdk." }, false),
	JUNIT(new String[] { "org.junit." }, true),
	ECLIPSE_JUNIT(new String[] { "org.eclipse.jdt.internal.junit.", "org.eclipse.jdt.internal.junit4." }, true),
	SUREFIRE_JUNIT(new String[] { "org.apache.maven.surefire." }, true);

	public static final Set<EntrypointFilter> ALL = EnumSet.allOf(EntrypointFilter.class);

	public static final Set<EntrypointFilter> NONTEST = EnumSet.allOf(EntrypointFilter.class).stream().filter(ef -> !ef.isTesting()).collect(Collectors.toSet());

	protected final String[] prefixes;

	protected final boolean testing;
}
