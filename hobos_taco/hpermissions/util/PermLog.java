package hobos_taco.hpermissions.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public enum PermLog
{
	INSTANCE;

	private static boolean configured;
    private static Logger myLog;

	public static void configureLogging()
	{
		if (configured) return;
		configured = true;

		myLog = Logger.getLogger(Util.ID);
		myLog.setParent(Logger.getLogger("ForgeModLoader"));
	}

    public static void log(Level level, String format, Object... data)
    {
    	myLog.log(level, String.format(format, data));
    }

    public static void info(String format, Object... data)
    {
        log(Level.INFO, format, data);
    }
    
    public static void debug(String format)
    {
        if (Config.debug)
        {
            log(Level.INFO, format);
        }
    }

    public static void error(String format, Object... data)
    {
        log(Level.INFO, "WARNING: " + format, data);
    }

    public static Logger getLogger()
    {
        return myLog;
    }
    
    public static void chatError(ICommandSender sender, String msg)
    {
    	if (sender instanceof EntityPlayer)
    	{
    		((EntityPlayer) sender).addChatMessage(("ERROR: " + msg));
    	}
    }
}