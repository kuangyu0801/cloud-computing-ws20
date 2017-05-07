package de.ustutt.iaas.cc.core;

public class QueueTextProcessor implements ITextProcessor {

    private final String requestQueueName;
    private final String responseQueueName;

    public QueueTextProcessor(String requestQueueName, String responseQueueName) {
	super();
	this.requestQueueName = requestQueueName;
	this.responseQueueName = responseQueueName;
    }

    @Override
    public String process(String text) {
	// TODO
	return text;
    }

}
