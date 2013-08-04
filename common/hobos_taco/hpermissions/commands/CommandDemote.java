package hobos_taco.hpermissions.commands;

import hobos_taco.hpermissions.api.Permission;
import hobos_taco.hpermissions.data.PermissionManager;
import hobos_taco.hpermissions.data.Player;
import hobos_taco.hpermissions.util.ChatHandler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;

@Permission("hpermissions.demote")
public class CommandDemote extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "hpermdemote";
    }
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;
    }
    
    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/" + this.getCommandName() + " <player>";
    }
    
    @Override
    public List<String> getCommandAliases()
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("hpd");
        list.add("hpdemote");
        list.add("hpdem");
        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] string)
    {
    	if (string.length != 1)
    	{
    		ChatHandler.chatError(sender, "Incorrect parameters: " + "/" + this.getCommandName() + " <player>");
    	}
    	else
    	{
	        Player hplayer = Player.getPlayer(string[0]);
	        
	        if (hplayer != null)
	        {
	            if (PermissionManager.demote(sender, string[0]))
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