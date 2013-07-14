package hobos_taco.hpermissions.commands;

import hobos_taco.hpermissions.Rank;
import hobos_taco.hpermissions.api.Permission;
import hobos_taco.hpermissions.data.Player;
import hobos_taco.hpermissions.data.PlayerData;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;

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
        return "/" + this.getCommandName() + "<player>";
    }
    
    @Override
    public List getCommandAliases()
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("hpd");
        list.add("hdemote");
        list.add("hdem");
        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] string)
    {
        Player hplayer = PlayerData.getPlayer(string[0]);
        
        if (hplayer != null)
        {
            if (Rank.demote(string[0]) == false)
            {
            	if (sender instanceof EntityPlayer)
            	{
            		((EntityPlayer) sender).addChatMessage("Player has no group set to be demoted to.");
            	}
            }  
            else
            {
                PlayerData.savePlayer(string[0]);
            }
        }
        else
        {
            throw new PlayerNotFoundException();
        }
    }
}