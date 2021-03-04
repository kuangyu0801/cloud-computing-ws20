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

/**
 * A very simple DAO implementation without any persistence. Data storage relies
 * on a {@link java.util.concurrent.ConcurrentMap} and ID generation is realized
 * using {@link java.util.concurrent.atomic.AtomicInteger}.
 * 
 * @author hauptfn
 *
 */
public class SimpleNotebookDAO implements INotebookDAO {

	private final static Logger logger = LoggerFactory.getLogger(SimpleNotebookDAO.class);

	// used for generating unique IDs
	private final AtomicInteger idCounter;

	// <note ID, note>
	private ConcurrentMap<String, NoteWithText> notebook;

	public SimpleNotebookDAO() {
		this.idCounter = new AtomicInteger();
		this.notebook = new ConcurrentHashMap<String, NoteWithText>();
	}

	@Override
	public Set<Note> getNotes() {
		Set<Note> result = new HashSet<Note>();
		// for each note in the internal storage, create a note without text
		for (NoteWithText note : notebook.values()) {
			result.add(new Note(note.getId(), note.getAuthor()));
		}
		return result;
	}

	@Override
	public NoteWithText getNote(String noteID) {
		NoteWithText result = null;
		// get note
		NoteWithText nwt = notebook.get(noteID);
		if (nwt != null) {
			// clone object to avoid side effects (modifications are only
			// allowed using the createOrUpdateNote method)
			result = new NoteWithText(nwt.getId(), nwt.getAuthor(), nwt.getText());
		}
		return result;
	}

	@Override
	public NoteWithText createOrUpdateNote(NoteWithText note) {
		NoteWithText result = null;
		if (note != null) {
			// if not yet defined, generate and set the ID
			if (StringUtils.isBlank(note.getId())) {
				note.setId(Integer.toString(idCounter.incrementAndGet()));
				logger.debug("Generated ID for note {}.", note.getId());
			}
			// store the note (overwrites note with same ID, if already present)
			notebook.put(note.getId(), note);
			// clone object to avoid side effects (modifications are only
			// allowed using the createOrUpdateNote method)
			result = new NoteWithText(note.getId(), note.getAuthor(), note.getText());
		}
		return result;
	}

	@Override
	public void deleteNote(String noteID) {
		// remove note, if present
		notebook.remove(noteID);
	}

}
