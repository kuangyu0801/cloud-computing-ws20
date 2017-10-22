package de.ustutt.iaas.cc;

import javax.validation.constraints.NotNull;

public class TextProcessorConfiguration {

    public static enum Mode {
	local, remoteSingle, queue
    };

    public static enum MOM {
	SQS, ActiveMQ
    };

    @NotNull
    public Mode mode;

    public String textProcessorResource;
    
    public MOM mom;
    
    public String activeMQurl;
    
    public String requestQueueName;
    
    public String responseQueueName;

}
