package de.ustutt.iaas.cc.core;

import java.util.Set;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.NoteWithText;

public interface INotebookDAO {

	public Set<Note> getNotes();
	
	public NoteWithText getNote(String noteID);
	
	public NoteWithText createOrUpdateNote(NoteWithText note);
	
	public void deleteNote(String noteID);
	
}
