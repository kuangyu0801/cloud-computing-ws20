package de.ustutt.iaas.cc;

import javax.validation.constraints.NotNull;

public class NotesDatabaseConfiguration {

	public static enum Mode {
		tmp, jdbc, gcds
	};

	@NotNull
	public Mode mode;

}
