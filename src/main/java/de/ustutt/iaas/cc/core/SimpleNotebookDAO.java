package de.ustutt.iaas.cc.core;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.Notebook;

public class SimpleNotebookDAO implements INotebookDAO {

	private static AtomicInteger idCounter = new AtomicInteger();

	private Notebook notebook;
	
	public SimpleNotebookDAO() {
		this.notebook = new Notebook();
		notebook.setNotes(new HashSet<Note>());
	}
	
	@Override
	public Notebook getNotebook() {
		return notebook;
	}

	@Override
	public void addNote(Note note) {
		note.setId(Integer.toString(idCounter.incrementAndGet()));
		notebook.getNotes().add(note);
	}

}
