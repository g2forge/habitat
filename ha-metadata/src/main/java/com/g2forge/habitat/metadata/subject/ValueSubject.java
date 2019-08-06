package com.g2forge.habitat.metadata.subject;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ValueSubject implements ISubject {
	protected final Object value;
}
