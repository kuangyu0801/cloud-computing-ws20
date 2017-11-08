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
	@Valid
	public SwaggerBundleConfiguration swaggerBundleConfiguration;

	/**
	 * JERSEY CLIENT
	 */
	@NotNull
	@Valid
	private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

	@JsonProperty("jerseyClient")
	public JerseyClientConfiguration getJerseyClientConfiguration() {
		return jerseyClient;
	}

	/**
	 * DATABASE
	 */
	@Valid
	private DataSourceFactory database;

	@JsonProperty("database")
	public DataSourceFactory getDataSourceFactory() {
		return database;
	}

	public void setDataSourceFactory(DataSourceFactory factory) {
		this.database = factory;
	}

	/**
	 * NOTEBOOK APPLICATION
	 */
	@JsonProperty("serviceInstanceID")
	public String serviceInstanceID;

	@JsonProperty("textProcessor")
	@NotNull
	@Valid
	public TextProcessorConfiguration textProcessorConfiguration;

	@JsonProperty("notesDB")
	@NotNull
	@Valid
	public NotesDatabaseConfiguration notesDatabaseConfiguration;

}
