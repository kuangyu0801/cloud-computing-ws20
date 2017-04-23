package de.ustutt.iaas.cc.core;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.NoteWithText;

public class SimpleNotebookDAO implements INotebookDAO {

    private final static Logger logger = LoggerFactory.getLogger(SimpleNotebookDAO.class);

    private static AtomicInteger idCounter = new AtomicInteger();

    private ConcurrentMap<String, NoteWithText> notebook;

    public SimpleNotebookDAO() {
	this.notebook = new ConcurrentHashMap<String, NoteWithText>();
    }

    @Override
    public Set<Note> getNotes() {
	Set<Note> result = new HashSet<Note>();
	for (NoteWithText note : notebook.values()) {
	    result.add(new Note(note.getId(), note.getAuthor()));
	}
	return result;
    }

    @Override
    public NoteWithText getNote(String noteID) {
	NoteWithText result = null;
	NoteWithText nwt = notebook.get(noteID);
	if (nwt != null) {
	    result = new NoteWithText(nwt.getId(), nwt.getAuthor(), nwt.getText());
	}
	return result;
    }

    @Override
    public NoteWithText createOrUpdateNote(NoteWithText note) {
	NoteWithText result = null;
	if (note != null) {
	    if (StringUtils.isBlank(note.getId())) {
		note.setId(Integer.toString(idCounter.incrementAndGet()));
	    }
	    notebook.put(note.getId(), note);
	    result = new NoteWithText(note.getId(), note.getAuthor(), note.getText());
	}
	return result;
    }

    @Override
    public void deleteNote(String noteID) {
	notebook.remove(noteID);
    }

}
