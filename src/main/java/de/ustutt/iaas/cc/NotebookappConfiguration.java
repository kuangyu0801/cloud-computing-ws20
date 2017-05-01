package de.ustutt.iaas.cc;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class NotebookappConfiguration extends Configuration {

    /**
     * SWAGGER
     */
    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    /**
     * JERSEY CLIENT
     */
    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
	return jerseyClient;
    }

    /**
     * DATABASE
     */
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
	this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
	return database;
    }

    /**
     * NOTEBOOK APPLICATION
     */
    @JsonProperty("serviceInstanceID")
    public String serviceInstanceID;

    @JsonProperty("textProcessor")
    @NotNull
    public TextProcessorConfiguration textProcessorConfiguration;

    @JsonProperty("notesDB")
    @NotNull
    public NotesDatabaseConfiguration notesDatabaseConfiguration;

}
