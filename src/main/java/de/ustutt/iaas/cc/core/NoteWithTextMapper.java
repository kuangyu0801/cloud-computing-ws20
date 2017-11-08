package de.ustutt.iaas.cc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import de.ustutt.iaas.cc.api.NoteWithText;

/**
 * JDBI result mapper. Takes a SQL result set and maps the current row to a
 * NoteWithText object.
 * 
 * @author hauptfn
 *
 */
public class NoteWithTextMapper implements ResultSetMapper<NoteWithText> {

	@Override
	public NoteWithText map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new NoteWithText(String.valueOf(r.getInt("id")), r.getString("author"), r.getString("text"));
	}

}
