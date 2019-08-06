package com.g2forge.habitat.metadata.subject;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class CombinedSubject {
	@Singular
	protected final List<ISubject> subjects;
}
