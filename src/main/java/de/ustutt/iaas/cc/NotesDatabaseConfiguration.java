package de.ustutt.iaas.cc;

public class NotesDatabaseConfiguration {

    public static enum Mode {
	tmp, jdbc, gcds
    };

    public Mode mode;

}
