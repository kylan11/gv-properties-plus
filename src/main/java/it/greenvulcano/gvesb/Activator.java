package it.greenvulcano.gvesb;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import it.greenvulcano.util.metadata.PropertiesHandler;
import it.greenvulcano.util.metadata.properties.GVESBPlusPropertyHandler;

public class Activator implements BundleActivator {
	
	@Override
	public void start(BundleContext context) throws Exception {

		// register the operations associated to the MongoDB connector
		PropertiesHandler.registerHandler(new GVESBPlusPropertyHandler());
	}

	@Override
	public void stop(BundleContext context) throws Exception {

		PropertiesHandler.unregisterHandler(new GVESBPlusPropertyHandler());

	}

}
