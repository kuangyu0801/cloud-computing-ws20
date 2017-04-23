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

import com.codahale.metrics.annotation.Timed;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.NoteWithText;
import de.ustutt.iaas.cc.core.INotebookDAO;
import de.ustutt.iaas.cc.core.ITextProcessor;
import io.swagger.annotations.Api;

@Path("")
@Api(value = "Notebook")
public class NotebookResource {

    INotebookDAO dao;
    ITextProcessor processor;

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
	NoteWithText result = dao.createOrUpdateNote(note);
	result.setText(processor.process(result.getText()));
	return result;
    }

    @GET
    @Path("/{noteID}")
    @Produces({ MediaType.APPLICATION_JSON })
    @Timed
    public NoteWithText getNote(@PathParam(value = "noteID") String noteID) {
	NoteWithText result = dao.getNote(noteID);
	result.setText(processor.process(result.getText()));
	return result;
    }

    @PUT
    @Path("/{noteID}")
    @Produces({ MediaType.APPLICATION_JSON })
    @Timed
    public NoteWithText updateNote(@PathParam(value = "noteID") String noteID, NoteWithText note) {
	// just in case...
	note.setId(noteID);
	NoteWithText result = dao.createOrUpdateNote(note);
	result.setText(processor.process(result.getText()));
	return result;
    }

    @DELETE
    @Path("/{noteID}")
    @Produces({ MediaType.APPLICATION_JSON })
    @Timed
    public void deleteNote(@PathParam(value = "noteID") String noteID) {
	dao.deleteNote(noteID);
    }

}
