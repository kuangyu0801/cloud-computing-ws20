package de.ustutt.iaas.cc.core;

import java.util.Set;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.NoteWithText;

/**
 * DAO for accessing a set of notes. Distinguishes between notes without text
 * and notes with text (for e.g. performance reasons, as the text might be
 * arbitrary big, whereas the other data is rather small).
 * 
 * @author hauptfn
 *
 */
public interface INotebookDAO {

	/**
	 * Returns all notes (without text).
	 * 
	 * @return Set of all notes (without text).
	 */
	public Set<Note> getNotes();

	/**
	 * Returns the note with the given ID (with text).
	 * 
	 * @param noteID
	 *            The ID of the requested note.
	 * @return The note with the given ID (with text), or null if the ID does not
	 *         exist.
	 */
	public NoteWithText getNote(String noteID);

	/**
	 * Creates or updates a note. If the note contains no ID, a new note is created
	 * and an ID is assigned to it. If the note contains an ID, the corresponding
	 * note is updated (overwritten). If there exists no note with the given ID, a
	 * new note is created and the given ID may be replaced by a different ID.
	 * 
	 * @param note
	 *            The note to create or update.
	 * @return The note that has been created or updated.
	 */
	public NoteWithText createOrUpdateNote(NoteWithText note);

	/**
	 * Deletes a note.
	 * 
	 * @param noteID
	 *            The ID of the note to delete. If the ID does not exist, nothing
	 *            happens.
	 */
	public void deleteNote(String noteID);

}
