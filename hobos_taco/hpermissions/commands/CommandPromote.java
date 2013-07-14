package hobos_taco.hpermissions.commands;

import hobos_taco.hpermissions.Rank;
import hobos_taco.hpermissions.api.Permission;
import hobos_taco.hpermissions.data.Player;
import hobos_taco.hpermissions.data.PlayerData;
import hobos_taco.hpermissions.util.PermLog;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;

@Permission("hpermissions.promote")
public class CommandPromote extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "hpermpromote";
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
        list.add("hpp");
        list.add("hpromote");
        list.add("hprom");
        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] string)
    {
        PermLog.debug("processCommand");
        PermLog.debug("string[0] = " + string[0]);

        Player hplayer = PlayerData.getPlayer(string[0]);
        
        if (hplayer != null)
        {
            PermLog.debug("hplayer != null");

            if (Rank.promote(string[0]) == false)
            {
                PermLog.debug("Rank.promote(string[0]) == false");
                if (sender instanceof EntityPlayer)
            	{
            		((EntityPlayer) sender).addChatMessage("Player has no group set to be promoted to.");
            	}
            }   
            else
            {
                PermLog.debug("savePlayer");
                PlayerData.savePlayer(string[0]);
            }
        }
        else
        {
            throw new PlayerNotFoundException();
        }
    }
}