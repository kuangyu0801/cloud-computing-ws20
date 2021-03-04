package de.ustutt.iaas.cc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import de.thomaskrille.dropwizard_template_config.TemplateConfigVariablesProvider;

/**
 * Reads properties from a properties file and provides them for use in
 * Freemarker templates.
 * <p>
 * Note: logging is not working yet as we are processing the config file
 * _before_ it is read (and the config file configures the loggers...)
 * 
 * @author hauptfn
 *
 */
public class PropertiesFileTemplateConfigVariablesProvider implements TemplateConfigVariablesProvider {

	// properties file to be read
	private final String propFilename;
	// namespace under which the properties will be accessible in Freemarker
	private final String namespace;

	public PropertiesFileTemplateConfigVariablesProvider(String propFilename, String namespace) {
		super();
		this.propFilename = propFilename;
		this.namespace = namespace;
	}

	@Override
	public String getNamespace() {
		return namespace;
	}

	@Override
	public Map<String, String> getVariables() {
		Properties prop = new Properties();
		try (InputStream is = new FileInputStream(Paths.get(propFilename).toFile())) {
			prop.load(is);
		} catch (FileNotFoundException e1) {
			System.err.println(propFilename + " not found");
		} catch (IOException e) {
			System.err.println("Error reading " + propFilename);
		}
		Map<String, String> result = new HashMap<String, String>();
		for (String key : prop.stringPropertyNames()) {
			// TODO only for debugging, might contain sensitive data!
			System.out.println("Registering <" + key + "," + prop.getProperty(key) + "> for freemarker.");
			result.put(key, prop.getProperty(key));
		}
		return result;
	}

}
