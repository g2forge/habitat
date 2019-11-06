package com.g2forge.habitat.metadata.value.subject;

import java.util.Collection;

public interface IMergedSubject extends ISubject {
	public Collection<? extends ISubject> getSubjects();
}
