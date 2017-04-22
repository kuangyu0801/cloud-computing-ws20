package de.ustutt.iaas.cc;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ustutt.iaas.cc.core.INotebookDAO;
import de.ustutt.iaas.cc.core.ITextProcessor;
import de.ustutt.iaas.cc.core.LocalTextProcessor;
import de.ustutt.iaas.cc.core.SimpleNotebookDAO;
import de.ustutt.iaas.cc.resources.NotebookResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
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
	// swagger
	bootstrap.addBundle(new SwaggerBundle<NotebookappConfiguration>() {
	    protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(NotebookappConfiguration configuration) {
		return configuration.swaggerBundleConfiguration;
	    }
	});
    }

    @Override
    public void run(final NotebookappConfiguration configuration, final Environment environment) {
	myID = configuration.getServiceInstanceID();
	if (StringUtils.isBlank(myID)) {
	    logger.debug("setting random service instance ID");
	    myID = UUID.randomUUID().toString();
	}
	logger.info("Service Instance ID is: "+myID);
	final ITextProcessor tp = new LocalTextProcessor(myID);
	final INotebookDAO dao = new SimpleNotebookDAO();
	final NotebookResource root = new NotebookResource(dao, tp);
	environment.jersey().register(root);
    }

}
