package de.ustutt.iaas.cc.core;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.NoteWithText;

public class DatabaseNotebookDAO implements INotebookDAO {

	private final static Logger logger = LoggerFactory.getLogger(DatabaseNotebookDAO.class);

	private final INotesDB dbAccess;

	public DatabaseNotebookDAO(INotesDB dbAccess) {
		super();
		this.dbAccess = dbAccess;
	}

	@Override
	public Set<Note> getNotes() {
		return dbAccess.getAll();
	}

	@Override
	public NoteWithText getNote(String noteID) {
		if (!Strings.isNullOrEmpty(noteID)) {
			return dbAccess.get(noteID);
		}
		logger.warn("Cannot retrieve note with empty id.");
		return null;
	}

	@Override
	public NoteWithText createOrUpdateNote(NoteWithText note) {
		// create or update
		String id = null;
		if (Strings.isNullOrEmpty(note.getId())) {
			id = dbAccess.insert(note.getAuthor(), note.getText());
			logger.debug("Created note in DB with id {}.", id);
		} else {
			id = note.getId();
			// TODO check result if all was ok?
			int result = dbAccess.update(note.getId(), note.getAuthor(), note.getText());
			logger.debug("Update of note {} changed {} rows in DB.", id, result);
		}
		// retrieve created or updated note from DB
		if (id != null) {
			return dbAccess.get(id);
		}
		return null;
	}

	@Override
	public void deleteNote(String noteID) {
		if (!Strings.isNullOrEmpty(noteID)) {
			int result = dbAccess.delete(noteID);
			if (result <= 0) {
				logger.warn("Cannot delete note {}", noteID);
			} else {
				logger.debug("Delete of note {} changed {} rows in DB.", noteID, result);
			}
		} else {
			logger.warn("Cannot delete note with empty id.");
		}
	}

}
