package com.g2forge.habitat.metadata.annotations.implementations;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.g2forge.alexandria.java.reflect.JavaScope;
import com.g2forge.habitat.metadata.annotations.IJavaAnnotated;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class JavaAnnotated implements IJavaAnnotated {
	@Singular
	protected final List<Annotation> annotations;

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> type) {
		for (Annotation annotation : annotations) {
			if (type.isInstance(annotation)) {
				@SuppressWarnings("unchecked")
				final T retVal = (T) annotation;
				return retVal;
			}
		}
		return null;
	}

	@Override
	public Collection<? extends Annotation> getAnnotations(JavaScope scope) {
		return Collections.unmodifiableList(getAnnotations());
	}

	@Override
	public boolean isAnnotated(Class<? extends Annotation> type) {
		for (Annotation annotation : annotations) {
			if (type.isInstance(annotation)) return true;
		}
		return false;
	}
}
