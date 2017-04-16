package de.ustutt.iaas.cc.api;

import java.util.Set;

public class Notebook {

	private Set<Note> notes;

	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

}
