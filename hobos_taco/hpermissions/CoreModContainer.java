package hobos_taco.hpermissions;

import hobos_taco.hpermissions.api.Permission;
import hobos_taco.hpermissions.commands.CommandDemote;
import hobos_taco.hpermissions.commands.CommandPromote;
import hobos_taco.hpermissions.commands.CommandReload;
import hobos_taco.hpermissions.commands.CommandSet;
import hobos_taco.hpermissions.data.Group;
import hobos_taco.hpermissions.data.GroupData;
import hobos_taco.hpermissions.data.Player;
import hobos_taco.hpermissions.data.PlayerData;
import hobos_taco.hpermissions.util.Config;
import hobos_taco.hpermissions.util.PermLog;
import hobos_taco.hpermissions.util.Util;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
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
        PermLog.debug("commandEvent");
        PermLog.debug(event.command.getClass().getName());

        if (event.command.getClass().isAnnotationPresent(Permission.class))
        {
            PermLog.debug("anno pres");
            String perm = event.command.getClass().getAnnotation(Permission.class).value();
            PermLog.debug("perm = " + perm);

            if (event.sender instanceof EntityPlayer)
            {
                PermLog.debug("event.sender instanceof EntityPlayer");
                EntityPlayer player = (EntityPlayer) event.sender;
                
                if (PermissionManager.hasPermission(player.username, perm) == false)
                {
                    PermLog.debug("PermissionManager.hasPermission(player.username, perm) == false");
                    player.addChatMessage("You do not have permission to use this command.");
                    event.setCanceled(true);
                }
                else
                {
                    PermLog.debug("Exec command");
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
            PermLog.configureLogging();
            GameRegistry.registerPlayerTracker(new PlayerHandler());

            File file = new File(MinecraftServer.getServer().getFolderName(), "hPermissions");
            file.mkdir();
            Util.HPERMFOLDER = file;
            
            Config.init(event);
        }
    }

	@Subscribe
    public void serverStart(FMLServerStartingEvent event)
    {
        PermLog.info("hPermissions " + Util.VERSION + " starting up...");
                
        File file1 = new File(Util.HPERMFOLDER, "Groups");
        file1.mkdir();
        Util.HPERMGROUPFOLDER = file1;
        
        PermLog.info(Util.HPERMGROUPFOLDER.listFiles().length + " Groups found");
        
        if (Util.HPERMGROUPFOLDER.listFiles().length == 0)
        {
            PermLog.info("Creating default groups");
            GroupData.createDefaultGroups();
        }
       
        GroupData.loadAllGroups();
                
        PermLog.info(Util.GROUPSLOADED + " Groups loaded");
        event.registerServerCommand(new CommandReload());
        event.registerServerCommand(new CommandPromote());
        event.registerServerCommand(new CommandDemote());
        event.registerServerCommand(new CommandSet());
        PermLog.info("hPermissions loaded");
    }
    
    @ForgeSubscribe
    public void serverStop(FMLServerStoppingEvent event)
    {
        PermLog.info("hPermissions " + Util.VERSION + " shutting down...");
             
        GroupData.saveAllGroups();
        PlayerData.saveAllPlayers();

        PermLog.info("hPermissions saved and shut down.");
    }
}