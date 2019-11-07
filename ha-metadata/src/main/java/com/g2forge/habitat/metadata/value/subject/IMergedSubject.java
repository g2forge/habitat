package com.g2forge.habitat.metadata.value.subject;

import java.util.Collection;

import com.g2forge.habitat.metadata.type.subject.IMergedSubjectType;

@SubjectType(IMergedSubjectType.class)
public interface IMergedSubject extends ISubject {
	public Collection<? extends ISubject> getSubjects();
}
