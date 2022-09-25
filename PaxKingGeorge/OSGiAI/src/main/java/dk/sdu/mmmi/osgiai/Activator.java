package dk.sdu.mmmi.osgiai;

import dk.sdu.mmmi.cbse.common.services.IAISPI;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        AIController ai = new AIController();
        context.registerService(IAISPI.class, ai, null);
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
