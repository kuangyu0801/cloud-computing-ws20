package de.ustutt.iaas.cc.core;

import java.util.Set;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import de.ustutt.iaas.cc.api.Note;
import de.ustutt.iaas.cc.api.NoteWithText;

/**
 * Realizes SQL database access based on JDBI.
 * <p>
 * http://jdbi.org/sql_object_api_queries/
 * <p>
 * http://jdbi.org/sql_object_api_dml/
 * 
 * @author hauptfn
 *
 */
@RegisterMapper({ NoteWithTextMapper.class, NoteMapper.class })
public interface INotesDB {

	@SqlUpdate("insert into notes (author, text) values (:author, :text)")
	@GetGeneratedKeys
	public String insert(@Bind("author") String author, @Bind("text") String text);

	@SqlUpdate("update notes set author = :author, text = :text where id = :id")
	public int update(@Bind("id") String id, @Bind("author") String author, @Bind("text") String text);

	@SqlQuery("select id, author, text from notes where id = :id")
	public NoteWithText get(@Bind("id") String id);

	@SqlQuery("select id, author from notes")
	public Set<Note> getAll();

	@SqlUpdate("delete from notes where id = :id")
	public int delete(@Bind("id") String id);

}
