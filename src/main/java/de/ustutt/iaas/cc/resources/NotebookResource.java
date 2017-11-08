package de.ustutt.iaas.cc.resources;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.NoteWithText;
import de.ustutt.iaas.cc.core.INotebookDAO;
import de.ustutt.iaas.cc.core.ITextProcessor;
import io.swagger.annotations.Api;

/**
 * The resource class providing all methods offered by the REST API.
 * 
 * @author hauptfn
 *
 */
@Path("")
@Api(value = "Notebook")
public class NotebookResource {

	private final static Logger logger = LoggerFactory.getLogger(NotebookResource.class);

	// DAO, used for data access
	private final INotebookDAO dao;
	// text processor, used for processing text before returning it
	private final ITextProcessor processor;

	public NotebookResource(INotebookDAO dao, ITextProcessor processor) {
		this.dao = dao;
		this.processor = processor;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Timed
	public Set<Note> getNotes() {
		return dao.getNotes();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Timed
	public NoteWithText createNote(NoteWithText note) {
		if (note != null) {
			// create note
			NoteWithText result = dao.createOrUpdateNote(note);
			if (result != null) {
				// process its text
				result.setText(processor.process(result.getText()));
				// return note with processed text
				return result;
			} else {
				logger.warn("Error creating or updating note {} ({})", note.getId(), note);
			}
		}
		return null;
	}

	@GET
	@Path("/{noteID}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Timed
	public NoteWithText getNote(@PathParam(value = "noteID") String noteID) {
		// get note (if present)
		NoteWithText result = dao.getNote(noteID);
		if (result != null) {
			// process its text
			result.setText(processor.process(result.getText()));
		}
		// return note with processed text
		return result;
	}

	@PUT
	@Path("/{noteID}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Timed
	public NoteWithText updateNote(@PathParam(value = "noteID") String noteID, NoteWithText note) {
		if (note != null) {
			// just in case...
			note.setId(noteID);
			// update note (or create it, if it does not yet exist)
			NoteWithText result = dao.createOrUpdateNote(note);
			// process its text
			result.setText(processor.process(result.getText()));
			// return note with processed text
			return result;
		}
		return null;
	}

	@DELETE
	@Path("/{noteID}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Timed
	public void deleteNote(@PathParam(value = "noteID") String noteID) {
		// delete note if present
		dao.deleteNote(noteID);
	}

}
