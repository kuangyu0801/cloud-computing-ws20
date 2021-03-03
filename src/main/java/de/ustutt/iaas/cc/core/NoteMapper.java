package de.ustutt.iaas.cc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import de.ustutt.iaas.cc.api.Note;

/**
 * JDBI result mapper. Takes a SQL result set and maps the current row to a Note
 * object.
 * 
 * @author hauptfn
 *
 */
public class NoteMapper implements ResultSetMapper<Note> {

	@Override
	public Note map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new Note(String.valueOf(r.getInt("id")), r.getString("author"));
	}

}
