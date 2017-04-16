package de.ustutt.iaas.cc.core;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.Notebook;

public interface INotebookDAO {

	public Notebook getNotebook();
	
	public void createOrUpdateNote(Note note);
	
	public void deleteNote(Note note);
	
}
