package com.g2forge.habitat.metadata.type.subject;

import java.lang.reflect.AnnotatedElement;
import java.util.Objects;

import com.g2forge.habitat.metadata.meta.IMetadataDescriptor;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class SubjectTypeDescriptor implements IMetadataDescriptor<ISubjectType, SubjectTypeDescriptor> {
	protected final Class<? extends AnnotatedElement> elementType;

	protected final Class<?> valueType;

	@Override
	public boolean isMatch(ISubjectType described) {
		if (described instanceof IValueSubjectType) {
			if (!Objects.equals(getValueType(), ((IValueSubjectType) described).getValue())) return false;
			if (!Objects.equals(getElementType(), ((IValueSubjectType) described).getElement())) return false;
			return true;
		}
		if (described instanceof IElementSubjectType) {
			if (getValueType() != null) return false;
			if (!Objects.equals(getElementType(), ((IElementSubjectType) described).getElement())) return false;
			return true;
		}
		return false;
	}

	@Override
	public SubjectTypeDescriptor reduce() {
		if ((getElementType() == null) && (getValueType() == null)) throw new NullPointerException("A subject type requires either an element or value type!");
		return this;
	}
}