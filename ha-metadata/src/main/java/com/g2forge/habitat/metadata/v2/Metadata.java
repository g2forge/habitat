package com.g2forge.habitat.metadata.v2;

import com.g2forge.habitat.metadata.v2.access.annotation.AnnotationMetadataRegistry;
import com.g2forge.habitat.metadata.v2.access.chained.ChainedMetadataRegistry;
import com.g2forge.habitat.metadata.v2.access.merged.MergedMetadataRegistry;
import com.g2forge.habitat.metadata.v2.type.MetadataTypeContext;
import com.g2forge.habitat.metadata.v2.value.IMetadataValueContext;
import com.g2forge.habitat.metadata.v2.value.MetadataValueContext;

import lombok.Getter;

public class Metadata implements IMetadata {
	@Getter(lazy = true)
	private static final IMetadata standard = new Metadata();

	@Getter(lazy = true)
	private final IMetadataValueContext context = new MetadataValueContext(new MetadataTypeContext(), new ChainedMetadataRegistry(new MergedMetadataRegistry(), new AnnotationMetadataRegistry()));
}