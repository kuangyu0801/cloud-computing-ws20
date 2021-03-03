package de.ustutt.iaas.cc.core;

import java.util.List;

import javax.ws.rs.client.Client;

/**
 * A text processor that sends the text to one of a set of remote REST API for
 * processing (and balances the load between them round-robin).
 * 
 * @author hauptfn
 *
 */
public class RemoteTextProcessorMulti implements ITextProcessor {

	public RemoteTextProcessorMulti(List<String> textProcessorResources, Client client) {
		super();
		// TODO ...
	}

	@Override
	public String process(String text) {
		// text processing placeholder, to be replaced by your solution :-)
		String processedText = "[processed by incomplete RemoteTextProcessorMulti] - " + text;

		// TODO send request to "next" text processor endpoint (following some load balancing strategy)

		return processedText;
	}

}
