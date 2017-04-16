package de.ustutt.iaas.cc;

import de.ustutt.iaas.cc.core.INotebookDAO;
import de.ustutt.iaas.cc.core.SimpleNotebookDAO;
import de.ustutt.iaas.cc.resources.NotebookResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class NotebookappApplication extends Application<NotebookappConfiguration> {

    public static void main(final String[] args) throws Exception {
        new NotebookappApplication().run(args);
    }

    @Override
    public String getName() {
        return "notebookapp";
    }

    @Override
    public void initialize(final Bootstrap<NotebookappConfiguration> bootstrap) {
		// static assets (html, css, js, ...)
		bootstrap.addBundle(new AssetsBundle("/assets", "/"));
		// swagger
		bootstrap.addBundle(new SwaggerBundle<NotebookappConfiguration>() {
			protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
					NotebookappConfiguration configuration) {
				return configuration.swaggerBundleConfiguration;
			}
		});
    }

    @Override
    public void run(final NotebookappConfiguration configuration,
                    final Environment environment) {
    	final INotebookDAO dao = new SimpleNotebookDAO();
    	final NotebookResource root = new NotebookResource(dao);
    	environment.jersey().register(root);
    }

}
