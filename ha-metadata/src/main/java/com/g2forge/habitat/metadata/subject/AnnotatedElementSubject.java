package com.g2forge.habitat.metadata.subject;

import java.lang.reflect.AnnotatedElement;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class AnnotatedElementSubject implements ISubject {
	protected final AnnotatedElement annotatedElement;
}
