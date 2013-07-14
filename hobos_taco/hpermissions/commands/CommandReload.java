package hobos_taco.hpermissions.commands;

import hobos_taco.hpermissions.api.Permission;
import hobos_taco.hpermissions.data.GroupData;
import hobos_taco.hpermissions.data.PlayerData;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

@Permission("hpermissions.reload")
public class CommandReload extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "hpermreload";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;
    }
    
    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/" + this.getCommandName();
    }
    
    @Override
    public List getCommandAliases()
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("hpr");
        list.add("hreload");
        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] string)
    {
    	if (sender instanceof EntityPlayer)
    	{
    		((EntityPlayer) sender).addChatMessage("Reloading...");
    	}
    	
        GroupData.loadAllGroups();
        PlayerData.loadAllPlayers();
        
        if (sender instanceof EntityPlayer)
    	{
    		((EntityPlayer) sender).addChatMessage("Done.");
    	}
    }
}