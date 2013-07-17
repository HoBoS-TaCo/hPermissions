package hobos_taco.hpermissions.commands;

import hobos_taco.hpermissions.api.Permission;
import hobos_taco.hpermissions.api.PermissionManager;
import hobos_taco.hpermissions.data.Group;
import hobos_taco.hpermissions.data.Player;
import hobos_taco.hpermissions.util.ChatHandler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;

@Permission("hpermissions.set")
public class CommandSet extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "hpermset";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;
    }
    
    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/" + this.getCommandName() + " <player> <group>";
    }
    
    @Override
    public List getCommandAliases()
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("hps");
        list.add("hpset");
        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] string)
    {
    	if (string.length != 2)
    	{
    		ChatHandler.chatError(sender, "Incorrect parameters: " + "/" + this.getCommandName() + " <player> <group>");
    	}
    	else
    	{
	        Player player = Player.getPlayer(string[0]);
	        Group group = Group.getGroup(string[1]);
	
	        if (player != null)
	        {
	        	if (PermissionManager.setGroup(sender, string[0], string[1]))
		        {
	        		Player.savePlayer(string[0]);
		        }
	        }
	        else
	        {
	            throw new PlayerNotFoundException();
	        }
    	}
    }
}