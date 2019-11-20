package com.g2forge.habitat.metadata.value.subject;

import java.lang.reflect.AnnotatedElement;
import java.util.Objects;

import com.g2forge.habitat.metadata.meta.IMetadataDescriptor;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class SubjectDescriptor implements IMetadataDescriptor<ISubject, SubjectDescriptor> {
	protected final AnnotatedElement element;

	protected final Object value;

	@Override
	public boolean isMatch(ISubject described) {
		if (described instanceof IValueSubject) {
			if (!Objects.equals(getValue(), ((IValueSubject) described).getValue())) return false;
			if (!Objects.equals(getElement(), ((IValueSubject) described).getElement())) return false;
			return true;
		}
		if (described instanceof IElementSubject) {
			if (getValue() != null) return false;
			if (!Objects.equals(getElement(), ((IElementSubject) described).getElement())) return false;
			return true;
		}
		return false;
	}

	public SubjectDescriptor reduce() {
		if (element == null) {
			if (value == null) throw new NullPointerException("Cannot get metadata for a null element and value!");
			if (value instanceof AnnotatedElement) return new SubjectDescriptor((AnnotatedElement) value, null);
		}
		return new SubjectDescriptor(element, value);
	}
}