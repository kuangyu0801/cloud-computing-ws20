package de.ustutt.iaas.cc.core;

/**
 * A simple text processor that processes text without any remote communication.
 * The processor simply appends its ID to the given text.
 * 
 * @author hauptfn
 *
 */
public class LocalTextProcessor implements ITextProcessor {

	// the ID of this text processor
	private final String myID;

	/**
	 * Instantiates a text processor and assigns the given ID to it.
	 * 
	 * @param id
	 *            The ID of the text processor.
	 */
	public LocalTextProcessor(String id) {
		super();
		myID = id;
	}

	@Override
	public String process(String text) {
		return "[locally processed by " + myID + "] - " + text;
	}

}
