package de.ustutt.iaas.cc;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class NotebookappConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    private String serviceInstanceID;

    @JsonProperty("serviceInstanceID")
    public String getServiceInstanceID() {
	return serviceInstanceID;
    }

    public void setServiceInstanceID(String serviceInstanceID) {
	this.serviceInstanceID = serviceInstanceID;
    }

    public static enum Mode {
	A, B
    };

    private Mode mode;

    @JsonProperty("mode")
    @NotNull
    public Mode getMode() {
	return mode;
    }

    public void setMode(Mode mode) {
	this.mode = mode;
    }

    private String textProcessorResource;

    public String getTextProcessorResource() {
	return textProcessorResource;
    }

    public void setTextProcessorResource(String textProcessorResource) {
	this.textProcessorResource = textProcessorResource;
    }

    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
	return jerseyClient;
    }

}
