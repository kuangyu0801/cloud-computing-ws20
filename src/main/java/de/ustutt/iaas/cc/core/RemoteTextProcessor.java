package de.ustutt.iaas.cc.core;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * A text processor that sends the text to a remote REST API for processing.
 * 
 * @author hauptfn
 *
 */
public class RemoteTextProcessor implements ITextProcessor {

	private final WebTarget target;

	public RemoteTextProcessor(String textProcessorResource, Client client) {
		super();
		this.target = client.target(textProcessorResource);
	}

	@Override
	public String process(String text) {
		String processed = target.request(MediaType.TEXT_PLAIN).post(Entity.entity(text, MediaType.TEXT_PLAIN),
				String.class);
		return processed;
	}

}
