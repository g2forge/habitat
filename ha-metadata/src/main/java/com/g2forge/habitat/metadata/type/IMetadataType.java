package com.g2forge.habitat.metadata.type;

import com.g2forge.alexandria.annotations.note.Note;
import com.g2forge.alexandria.annotations.note.NoteType;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public interface IMetadataType {
	public <T> IPredicateType<T> predicate(Class<T> type);

	@Note(type = NoteType.TODO)
	public ISubjectType subject(Void TODO);
}
