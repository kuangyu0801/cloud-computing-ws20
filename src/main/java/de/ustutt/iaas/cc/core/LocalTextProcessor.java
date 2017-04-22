package de.ustutt.iaas.cc.core;

public class LocalTextProcessor implements ITextProcessor {

	private final String myID;
	
	public LocalTextProcessor(String id) {
		super();
		myID = id;
	}
	
	@Override
	public String process(String text) {
		return "[processed by "+myID+"] - "+text;
	}

}
