package hobos_taco.hpermissions.commands;

import hobos_taco.hpermissions.Rank;
import hobos_taco.hpermissions.api.Permission;
import hobos_taco.hpermissions.data.Group;
import hobos_taco.hpermissions.data.GroupData;
import hobos_taco.hpermissions.data.Player;
import hobos_taco.hpermissions.data.PlayerData;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;

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
        return "/" + this.getCommandName() + "<player> <group>";
    }
    
    @Override
    public List getCommandAliases()
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("hps");
        list.add("hset");
        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] string)
    {
        Player player = PlayerData.getPlayer(string[0]);
        Group group = GroupData.getGroup(string[1]);

        if ((player != null) && (group != null))
        {
            if (Rank.setGroup(string[0], string[1]) == false)
            {
            	if (sender instanceof EntityPlayer)
            	{
            		((EntityPlayer) sender).addChatMessage("Unknown Player or Group.");
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