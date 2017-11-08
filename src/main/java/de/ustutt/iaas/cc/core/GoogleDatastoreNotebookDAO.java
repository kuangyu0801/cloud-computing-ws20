package de.ustutt.iaas.cc.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.ProjectionEntity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.common.base.Strings;

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

	// TODO move to configuration file / class
	private final String projectID = "hauptfn-167617";
	private final String keyFilePath = "hauptfn-063896e58594_CloudDatastoreUser.json";

	private Datastore dsClient = null;

	public GoogleDatastoreNotebookDAO() {
		super();
		// create datastore client
		try {
			dsClient = DatastoreOptions.newBuilder().setProjectId(projectID)
					.setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(keyFilePath))).build()
					.getService();
		} catch (IOException e) {
			logger.error("Error creating client for Google Cloud Datastore.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Set<Note> getNotes() {
		Set<Note> result = new HashSet<Note>();

		// get all notes, but only id and author, not text
		Query<ProjectionEntity> queryNotes = Query.newProjectionEntityQueryBuilder().setKind("Note")
				.setProjection("author").build();
		QueryResults<ProjectionEntity> notes = dsClient.run(queryNotes);
		// process query results
		while (notes.hasNext()) {
			ProjectionEntity noteEntity = notes.next();
			result.add(new Note(Long.toString(noteEntity.getKey().getId()), noteEntity.getString("author")));
		}
		return result;
	}

	@Override
	public NoteWithText getNote(String noteID) {
		NoteWithText result = null;

		// get note entity for given ID
		Key noteKey = dsClient.newKeyFactory().setKind("Note").newKey(Long.parseLong(noteID));
		Entity note = dsClient.get(noteKey);

		// build result object
		result = new NoteWithText();
		result.setId(Long.toString(note.getKey().getId()));
		result.setText(note.getString("text"));
		result.setAuthor(note.getString("author"));
		return result;
	}

	@Override
	public NoteWithText createOrUpdateNote(NoteWithText note) {
		NoteWithText result = null;

		// build key for note (either generate new or use existing ID)
		Key noteKey;
		if (Strings.isNullOrEmpty(note.getId())) {
			// generate new key
			noteKey = dsClient.allocateId(dsClient.newKeyFactory().setKind("Note").newKey());
		} else {
			// use given key
			noteKey = dsClient.newKeyFactory().setKind("Note").newKey(Long.parseLong(note.getId()));
		}

		// store the note (overwrites note with same ID, if already present)
		Entity noteEntity = Entity.newBuilder(noteKey).set("author", note.getAuthor()).set("text", note.getText())
				.set("lastUpdate", Timestamp.now()).build();
		noteEntity = dsClient.put(noteEntity);

		// clone object to avoid side effects (modifications are only
		// allowed using the createOrUpdateNote method)
		result = new NoteWithText(Long.toString(noteEntity.getKey().getId()), note.getAuthor(), note.getText());
		return result;
	}

	@Override
	public void deleteNote(String noteID) {
		Key noteKey = dsClient.newKeyFactory().setKind("Note").newKey(Long.parseLong(noteID));
		dsClient.delete(noteKey);
	}

}
