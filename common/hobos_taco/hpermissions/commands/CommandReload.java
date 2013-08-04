package hobos_taco.hpermissions.commands;

import hobos_taco.hpermissions.api.Permission;
import hobos_taco.hpermissions.data.Group;
import hobos_taco.hpermissions.data.Player;
import hobos_taco.hpermissions.util.ChatHandler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

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
    public List<String> getCommandAliases()
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("hpr");
        list.add("hpreload");
        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] string)
    {
    	ChatHandler.chatConfirmation(sender, "Reloading...");
    	    	
        Group.loadAllGroups();
        Player.loadAllPlayers();
        
        ChatHandler.chatConfirmation(sender, "Done.");
    }
}