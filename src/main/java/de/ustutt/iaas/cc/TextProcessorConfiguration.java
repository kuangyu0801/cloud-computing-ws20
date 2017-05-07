package de.ustutt.iaas.cc;

public class TextProcessorConfiguration {

    public static enum Mode {
	local, remoteSingle, queue
    };

    public Mode mode;

    public String textProcessorResource;
    
    public String requestQueueName;
    
    public String responseQueueName;

}
