package com.g2forge.habitat.metadata;

import com.g2forge.alexandria.java.core.marker.Helpers;
import com.g2forge.habitat.metadata.accessor.AnnotationMetadataAccessor;
import com.g2forge.habitat.metadata.value.IMetadataValue;
import com.g2forge.habitat.metadata.value.MetadataValue;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
@Helpers
public class HMetadata {
	@MetadataValue(lazy = true)
	private static final IMetadataValue standard = new MetadataContext(AnnotationMetadataAccessor.create());
}
