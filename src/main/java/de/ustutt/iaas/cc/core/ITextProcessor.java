package de.ustutt.iaas.cc.core;

/**
 * A text processor. The semantic of the processing may be arbitrary.
 * 
 * @author hauptfn
 *
 */
public interface ITextProcessor {

	/**
	 * Processes the given text and returns the processed text. The text may be
	 * changed in any way (delete parts, add parts, replace parts, ...).
	 * 
	 * @param text
	 *            The text to be processed.
	 * @return The processed text.
	 */
	public String process(String text);

}
