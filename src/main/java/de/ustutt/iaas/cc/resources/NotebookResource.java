package de.ustutt.iaas.cc.resources;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.Notebook;
import de.ustutt.iaas.cc.core.INotebookDAO;
import io.dropwizard.views.View;
import io.swagger.annotations.Api;

@Path("")
@Api(value = "Notebook")
public class NotebookResource {

	INotebookDAO dao;

	public NotebookResource(INotebookDAO dao) {
		this.dao = dao;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Timed
	public Notebook getNotebook() {
		return dao.getNotebook();
	}

	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Timed
	public NotebookView getNotebookView() {
		return new NotebookView(dao.getNotebook());
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Timed
	public void createNote(Note note) {
		dao.addNote(note);
	}

	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Timed
	public NotebookView createNoteFromForm(@FormParam("author") String author, @FormParam("text") String text) {
		Note note = new Note();
		note.setAuthor(author);
		note.setText(text);
		dao.addNote(note);
		return new NotebookView(dao.getNotebook());
	}

	public class NotebookView extends View {
		private final Notebook notebook;

		protected NotebookView(Notebook notebook) {
			super("Notebook.ftl");
			this.notebook = notebook;
		}

		public Notebook getNotebook() {
			return notebook;
		}

		public Set<Note> getNotesSorted() {
			TreeSet<Note> result = new TreeSet<Note>(new Comparator<Note>() {
				@Override
				public int compare(Note o1, Note o2) {
					return o1.getId().compareTo(o2.getId());
				}
			});
			result.addAll(notebook.getNotes());
			return result;
		}
	}

}
