package com.g2forge.habitat.metadata.type.predicate;

import java.util.Objects;

import com.g2forge.habitat.metadata.meta.IMetadataDescriptor;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class PredicateTypeDescriptor implements IMetadataDescriptor<IPredicateType<?>, PredicateTypeDescriptor> {
	protected final Class<?> type;

	@Override
	public boolean isMatch(IPredicateType<?> described) {
		if (!Objects.equals(getType(), described.getPredicateID())) return false;
		return true;
	}

	@Override
	public PredicateTypeDescriptor reduce() {
		return this;
	}

}
