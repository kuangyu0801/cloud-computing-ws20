package de.ustutt.iaas.cc.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A text processor that sends the text to one of a set of remote REST API for
 * processing (and balances the load between them round-robin).
 * 
 * @author hauptfn
 *
 */
public class RemoteTextProcessorMulti implements ITextProcessor {

	private final static Logger logger = LoggerFactory.getLogger(RemoteTextProcessorMulti.class);

	List<WebTarget> targets = new LinkedList<WebTarget>();
	AtomicInteger ai = new AtomicInteger();

	public RemoteTextProcessorMulti(List<String> textProcessorResources, Client client) {
		super();
		for (String ep : textProcessorResources) {
			targets.add(client.target(ep));
		}
	}

	@Override
	public String process(String text) {
		int next = ai.getAndIncrement() % targets.size();
		logger.debug("Selecting endpoint at index {}.", next);
		String processedText = targets.get(next).request(MediaType.TEXT_PLAIN)
				.post(Entity.entity(text, MediaType.TEXT_PLAIN), String.class);
		return processedText;
	}

}
