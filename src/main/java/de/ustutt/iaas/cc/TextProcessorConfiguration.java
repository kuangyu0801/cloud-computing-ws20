package de.ustutt.iaas.cc;

public class TextProcessorConfiguration {

    public static enum Mode {
	local, remoteSingle
    };

    public Mode mode;

    public String textProcessorResource;

}
