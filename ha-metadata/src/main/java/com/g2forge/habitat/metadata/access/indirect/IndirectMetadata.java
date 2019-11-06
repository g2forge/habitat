package com.g2forge.habitat.metadata.access.indirect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.g2forge.habitat.metadata.access.IMetadataAccessor;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface IndirectMetadata {
	public Class<? extends IMetadataAccessor> value();
}
