package com.g2forge.habitat.metadata;

import org.junit.Ignore;
import org.junit.Test;

import com.g2forge.alexandria.annotations.note.Note;
import com.g2forge.alexandria.annotations.note.NoteType;
import com.g2forge.alexandria.test.HAssert;
import com.g2forge.habitat.metadata.TestAnnotationMetadata.Annotated;

import lombok.Builder;
import lombok.Data;

public class TestDynamicMetadata {
	@Data
	@Builder(toBuilder = true)
	@MetadataLoader(RecordLoader.class)
	public static class Record {
		protected final String string;
	}

	public static class RecordLoader implements IMetadataLoader {
		@Override
		@Note(type = NoteType.TODO, value = "Use static type switch", issue = "G2-432")
		public <T> T load(Class<T> type, IMetadata metadata) {
			if (!Record.class.equals(type)) throw new IllegalArgumentException();
			@SuppressWarnings("unchecked")
			final T retVal = (T) new Record("Hello");
			return retVal;
		}
	}

	@Test
	@Ignore
	public void record() {
		final Record record = IMetadata.of(Annotated.class).getMetadata(Record.class);
		HAssert.assertEquals("Hello", record.getString());
	}
}
