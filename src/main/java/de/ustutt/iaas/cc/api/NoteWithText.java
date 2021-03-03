package de.ustutt.iaas.cc.api;

/**
 * A note , comprising an ID, an author, and some text.
 * 
 * @author hauptfn
 *
 */
public class NoteWithText extends Note {

	private String text;

	public NoteWithText() {
		super();
	}

	public NoteWithText(String id, String author, String text) {
		super(id, author);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
