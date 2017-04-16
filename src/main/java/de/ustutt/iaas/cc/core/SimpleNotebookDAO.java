package de.ustutt.iaas.cc.core;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.Notebook;

public class SimpleNotebookDAO implements INotebookDAO {

	private final static Logger logger = LoggerFactory
			.getLogger(SimpleNotebookDAO.class);

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
	public void createOrUpdateNote(Note note) {
		if (note.getId() == null || note.getId().isEmpty() || note.getId().trim().isEmpty()) {
			note.setId(Integer.toString(idCounter.incrementAndGet()));
		} else {
			boolean removed = removeNote(notebook.getNotes(), note.getId());
			if (!removed) {
				logger.warn("Note with id '{}' not found.", note.getId());
			}
		}
		notebook.getNotes().add(note);
	}

	private boolean removeNote(Set<Note> notes, String id) {
		return notes.removeIf(n -> n.getId().equals(id));
	}

	@Override
	public void deleteNote(Note note) {
		notebook.getNotes().removeIf(n -> n.getId().equals(note.getId()));
	}

}
