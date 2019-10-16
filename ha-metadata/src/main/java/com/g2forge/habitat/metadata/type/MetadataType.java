package com.g2forge.habitat.metadata.type;

import java.lang.annotation.Annotation;

import com.g2forge.alexandria.annotations.note.Note;
import com.g2forge.alexandria.annotations.note.NoteType;
import com.g2forge.habitat.metadata.type.predicate.AnnotationPredicateType;
import com.g2forge.habitat.metadata.type.predicate.GeneralPredicateType;
import com.g2forge.habitat.metadata.type.predicate.IPredicateType;
import com.g2forge.habitat.metadata.type.subject.ISubjectType;

public class MetadataType implements IMetadataType {
	@Override
	public <T> IPredicateType<T> predicate(Class<T> type) {
		if (type.isAnnotation()) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			final IPredicateType<T> retVal = new AnnotationPredicateType(type.asSubclass(Annotation.class));
			return retVal;
		}
		return new GeneralPredicateType<>(type);
	}

	@Note(type = NoteType.TODO)
	@Override
	public ISubjectType subject(Void TODO) {
		// TODO Auto-generated method stub
		return null;
	}
}
