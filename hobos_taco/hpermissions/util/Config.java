package hobos_taco.hpermissions.util;

import java.io.File;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config
{
    public static String defaultGroup;
    public static boolean debug;
    
    public static void init(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(new File(Util.HPERMFOLDER, "hPermissions.cfg"));

        config.load();

        defaultGroup = config.get(Configuration.CATEGORY_GENERAL, "defaultGroup", "Default", "The group new players are put into.").getString();
        debug = config.get(Configuration.CATEGORY_GENERAL, "debug", false, "Log extra information.").getBoolean(false);
        
        config.save();
    }
}