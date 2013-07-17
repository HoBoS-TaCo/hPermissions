package hobos_taco.hpermissions;

import hobos_taco.hpermissions.api.Permission;
import hobos_taco.hpermissions.api.PermissionManager;
import hobos_taco.hpermissions.commands.CommandDemote;
import hobos_taco.hpermissions.commands.CommandPromote;
import hobos_taco.hpermissions.commands.CommandReload;
import hobos_taco.hpermissions.commands.CommandSet;
import hobos_taco.hpermissions.data.Group;
import hobos_taco.hpermissions.data.Player;
import hobos_taco.hpermissions.util.ChatHandler;
import hobos_taco.hpermissions.util.Config;
import hobos_taco.hpermissions.util.Util;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class CoreModContainer extends DummyModContainer
{
    public static HashMap<String, Group> groupList = new HashMap<String, Group>();
    public static HashMap<String, Player> playerList = new HashMap<String, Player>();
	public static Config config;
    
    public CoreModContainer()
    {
		super(new ModMetadata());
		ModMetadata myMeta = super.getMetadata();
		myMeta.authorList = Arrays.asList(new String[] { "HoBoS_TaCo" });
		myMeta.description = Util.DESC;
		myMeta.modId = Util.ID;
		myMeta.version = Util.VERSION;
		myMeta.name = Util.NAME;
		myMeta.url = "";
		myMeta.credits = Util.CREDITS;
    }

	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}
	
	@ForgeSubscribe
    public void commandEvent(CommandEvent event)
    {
        if (event.command.getClass().isAnnotationPresent(Permission.class))
        {
            String perm = event.command.getClass().getAnnotation(Permission.class).value();

            if (event.sender instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) event.sender;
                
                if (PermissionManager.hasPermission(player.username, perm) == false)
                {
                	ChatHandler.chatWarning(event.sender, "You do not have permission to use this command.");
                    event.setCanceled(true);
                }
                else
                {
                    event.command.processCommand(event.sender, event.parameters);
                    event.setCanceled(true);
                }
            }
        }
    }
	
	@Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        if (FMLCommonHandler.instance().getSide() == Side.SERVER)
        {
    		ChatHandler.log = Logger.getLogger(Util.ID);
    		ChatHandler.log.setParent(Logger.getLogger("ForgeModLoader"));
    		
    		GameRegistry.registerPlayerTracker(new PlayerHandler());

            File file = new File(MinecraftServer.getServer().getFolderName(), "hPermissions");
            file.mkdir();
            Util.HPERMFOLDER = file;
            
            Config.loadConfig();
        }
    }

	@Subscribe
    public void serverStart(FMLServerStartingEvent event)
    {
		ChatHandler.logInfo("hPermissions " + Util.VERSION + " starting up...");
                
        File file1 = new File(Util.HPERMFOLDER, "Groups");
        file1.mkdir();
        Util.HPERMGROUPFOLDER = file1;
        
        ChatHandler.logInfo(Util.HPERMGROUPFOLDER.listFiles().length + " Groups found");
        
        if (Util.HPERMGROUPFOLDER.listFiles().length == 0)
        {
        	ChatHandler.logInfo("Creating default groups");
            Group.createDefaultGroups();
        }
       
        Group.loadAllGroups();
                
        ChatHandler.logInfo(Util.GROUPSLOADED + " Groups loaded");
        event.registerServerCommand(new CommandReload());
        event.registerServerCommand(new CommandPromote());
        event.registerServerCommand(new CommandDemote());
        event.registerServerCommand(new CommandSet());
        ChatHandler.logInfo("hPermissions loaded");
    }
    
    @ForgeSubscribe
    public void serverStop(FMLServerStoppingEvent event)
    {
    	ChatHandler.logInfo("hPermissions " + Util.VERSION + " shutting down...");
             
        Group.saveAllGroups();
        Player.saveAllPlayers();

        ChatHandler.logInfo("hPermissions saved and shut down.");
    }
}