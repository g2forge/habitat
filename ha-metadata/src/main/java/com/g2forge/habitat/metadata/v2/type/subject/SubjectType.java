package com.g2forge.habitat.metadata.v2.type.subject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubjectType implements ISubjectType {
	Value,
	Element,
	ElementValue;
}
