package de.ustutt.iaas.cc.core;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.NoteWithText;

/**
 * DAO implementation that stores notes as entities in Google Datastore (NoSQL).
 * 
 * @author hauptfn
 *
 */
public class GoogleDatastoreNotebookDAO implements INotebookDAO {

    private final static Logger logger = LoggerFactory.getLogger(GoogleDatastoreNotebookDAO.class);

    public GoogleDatastoreNotebookDAO() {
    	super();
    	// TODO init
    }

    @Override
    public Set<Note> getNotes() {
    	Set<Note> result = new HashSet<Note>();
		// TODO get from google datastore
    	return result;
    }

    @Override
    public NoteWithText getNote(String noteID) {
    	NoteWithText result = null;
		// TODO get from google datastore
    	return result;
    }

    @Override
    public NoteWithText createOrUpdateNote(NoteWithText note) {
    	NoteWithText result = null;
    	// TODO create or update in google datastore
    	return result;
    }

    @Override
    public void deleteNote(String noteID) {
    	// TODO delete in google datastore
    }

}
