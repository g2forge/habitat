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
	INTELLIJ(new String[] { "com.intellij.rt." }, false),
	JDK(new String[] { "jdk.", "jdk.internal." }, false),
	YOURKIT(new String[] { "com.yourkit.runtime." }, false),
	SPRING(new String[] { "com.springsource.loaded.", "org.springsource.loaded." }, false),
	JAVASSIST(new String[] { "javassist." }, false),
	APACHEWEBBEANS(new String[] { "org.apache.webbeans." }, false),
	KOTLIN(new String[] { "kotlin.", "kotlinx." }, false),
	ANDROID(new String[] { "androidx.compose.runtime." }, false),
	MOCKITO(new String[] { "org.mockito." }, true),
	JUNIT(new String[] { "org.junit.", "junit." }, true),
	ECLIPSEJUNIT(new String[] { "org.eclipse.jdt.internal.junit.", "org.eclipse.jdt.internal.junit4." }, true),
	INTELLIJJUNIT(new String[] { "com.intellij.junit4." }, true),
	SUREFIRE(new String[] { "org.apache.maven.surefire." }, true);

	public static final Set<EntrypointFilter> ALL = EnumSet.allOf(EntrypointFilter.class);

	public static final Set<EntrypointFilter> NONTEST = EnumSet.allOf(EntrypointFilter.class).stream().filter(ef -> !ef.isTesting()).collect(Collectors.toSet());

	protected final String[] prefixes;

	protected final boolean testing;
}
