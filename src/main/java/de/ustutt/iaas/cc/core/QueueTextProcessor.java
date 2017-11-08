package de.ustutt.iaas.cc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ustutt.iaas.cc.TextProcessorConfiguration;

/**
 * A text processor that uses JMS to send text to a request queue and then waits
 * for the processed text on a response queue. For each text processing request,
 * a unique ID is generated that is later used to correlate responses to their
 * original request.
 * <p>
 * The text processing is realized by (one or more) workers that read from the
 * request queue and write to the response queue.
 * <p>
 * This implementation supports ActiveMQ as well as AWS SQS.
 * 
 * @author hauptfn
 *
 */
public class QueueTextProcessor implements ITextProcessor {

	private final static Logger logger = LoggerFactory.getLogger(QueueTextProcessor.class);

	public QueueTextProcessor(TextProcessorConfiguration conf) {
		super();
		logger.debug("Initializing QueueTextProcessor.");
		
		// TODO initialize JMS stuff, connect to queues, ...
		
	}

	@Override
	public String process(String text) {
		// text processing placeholder, to be replaced by your solution :-)
		String processedText = "[processed by incomplete QueueTextProcessor] - " + text;

		// TODO send text to request queue, receive processed text from response queue, return

		return processedText;
	}

}
