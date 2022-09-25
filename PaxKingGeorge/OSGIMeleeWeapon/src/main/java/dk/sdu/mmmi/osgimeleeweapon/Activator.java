package dk.sdu.mmmi.osgimeleeweapon;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.osgicommonmeleeweapon.MeleeWeaponSPI;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        MeleeWeaponProcessing mWeapon = new MeleeWeaponProcessing();
        
        context.registerService(IEntityProcessingService.class, mWeapon, null);    
        context.registerService(MeleeWeaponSPI.class, mWeapon, null);
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
