package hobos_taco.hpermissions.util;

import java.io.File;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config 
{	
	public static boolean debug;
	public static String defaultGroup;

	public static void init(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(new File(Util.HPERMFOLDER, "Config.cfg"));

        config.load();    
        
        debug = config.get(Configuration.CATEGORY_GENERAL, "debug-mode", true, "Log extra information").getBoolean(false);
        defaultGroup = config.get(Configuration.CATEGORY_GENERAL, "default-group", "Default", "Group new players are put into").getString();

		config.save();
	}
}
