package de.ustutt.iaas.cc;

import java.util.UUID;

import javax.ws.rs.client.Client;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ustutt.iaas.cc.core.INotebookDAO;
import de.ustutt.iaas.cc.core.ITextProcessor;
import de.ustutt.iaas.cc.core.LocalTextProcessor;
import de.ustutt.iaas.cc.core.RemoteTextProcessor;
import de.ustutt.iaas.cc.core.SimpleNotebookDAO;
import de.ustutt.iaas.cc.resources.NotebookResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class NotebookappApplication extends Application<NotebookappConfiguration> {

    private final static Logger logger = LoggerFactory.getLogger(NotebookappApplication.class);

    public static void main(final String[] args) throws Exception {
	new NotebookappApplication().run(args);
    }

    private String myID;

    @Override
    public String getName() {
	return "notebookapp";
    }

    @Override
    public void initialize(final Bootstrap<NotebookappConfiguration> bootstrap) {
	// static assets (html, css, js, ...)
	bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
	// swagger UI
	bootstrap.addBundle(new SwaggerBundle<NotebookappConfiguration>() {
	    protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(NotebookappConfiguration configuration) {
		return configuration.swaggerBundleConfiguration;
	    }
	});
    }

    @Override
    public void run(final NotebookappConfiguration configuration, final Environment environment) {
	// get service instance ID from config file
	myID = configuration.getServiceInstanceID();
	// if not set, generate random service instance ID
	if (StringUtils.isBlank(myID)) {
	    logger.debug("setting random service instance ID");
	    myID = UUID.randomUUID().toString();
	}
	logger.info("Service Instance ID is: " + myID);

	// check configured service mode
	ITextProcessor tp = null;
	INotebookDAO dao = null;
	switch (configuration.getMode()) {
	case A:
	    logger.debug("Mode A");
	    tp = new LocalTextProcessor(myID);
	    dao = new SimpleNotebookDAO();
	    break;
	case B:
	    logger.debug("Mode B");
	    final Client client = new JerseyClientBuilder(environment)
		    .using(configuration.getJerseyClientConfiguration()).build(getName());
	    tp = new RemoteTextProcessor(configuration.getTextProcessorResource(), client);
	    dao = new SimpleNotebookDAO();
	    break;
	default:
	    logger.warn("Unknown or empty mode ({}), defaulting to A", configuration.getMode());
	    tp = new LocalTextProcessor(myID);
	    dao = new SimpleNotebookDAO();
	    break;
	}

	// create and register resource class
	final NotebookResource root = new NotebookResource(dao, tp);
	environment.jersey().register(root);
    }

}
