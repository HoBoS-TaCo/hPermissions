package hobos_taco.hpermissions.data;

import hobos_taco.hpermissions.util.ChatHandler;
import hobos_taco.hpermissions.util.Util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommandSender;

public class PermissionManager
{
    /**
     * Check if the player has the perm
     * @param username - player username
     * @param perm - perm to be checked for
     * @return true if player has permission
     */
    private static boolean playerHasPerm(String username, String perm)
    {
        String group = Player.getPlayer(username).getGroup();

        String[] pPerms = Player.getPlayer(username).getPermissions();
        String[] gPerms = Group.getGroup(group).getPermissions();
        
        List<String> pList = new ArrayList<String>();
        if (pPerms != null)
        {
            pList.addAll(Util.arrayToArrayList(pPerms));
        }
        
        if (gPerms != null)
        {
            pList.addAll(Util.arrayToArrayList(gPerms));
        }
        
        if (pList.contains(perm))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Check if the player has the perm or the allaccess parameter
     * @param username - player username
     * @param perm - perm to be checked for
     * @return true if player has permission
     */
    public static boolean hasPermission(String username, String perm)
    {
        if (Player.getPlayer(username).isAllAccess() == true)
        {
            return true;
        }
        else if (playerHasPerm(username, perm))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Promote a player
     * @param sender
     * @param username
     * @return
     */
    public static boolean promote(ICommandSender sender, String username)
    {
        String playerGroupString = Player.getPlayer(username).getGroup();
        String nextGroupString = Group.getGroup(playerGroupString).getNextGroup();
        Group nextGroup =  Group.getGroup(nextGroupString);
        if (playerGroupString != null)
        {
            if (nextGroupString != null)
            {
                if (nextGroup != null)
                {
                	if (nextGroup.isPermCommandsDisabled() == false)
                    {
                        Player.getPlayer(username).setGroup(nextGroup.getGroupName());
                        ChatHandler.chatConfirmation(sender, username + " promoted to group " + nextGroupString + ".");
                        return true;
                    }
                	else
                	{
                        ChatHandler.chatError(sender, "You must promote to this group manually.");
                	}
                }
                else
                {
                    ChatHandler.chatError(sender, nextGroupString + " cannot be found.");
                }
            }
            else
            {
                ChatHandler.chatError(sender, "Player group cannot be found.");
            }
        }
        else
        {
            ChatHandler.chatError(sender, "Player has no next group set.");
        }
        return false;
    }
    
    /**
     * Demote a player
     * @param sender
     * @param username
     * @return
     */
    public static boolean demote(ICommandSender sender, String username)
    {
        String playerGroupString = Player.getPlayer(username).getGroup();
        String prevGroupString = Group.getGroup(playerGroupString).getPrevGroup();
        Group prevGroup = Group.getGroup(prevGroupString);
        Group playerGroup = Group.getGroup(playerGroupString);
        if (playerGroupString != null)
        {
            if (prevGroupString != null)
            {
                if (prevGroup != null)
                {
                	if (playerGroup != null)
                    {
                		if (playerGroup.isPermCommandsDisabled() == false)
                        {
                			Player.getPlayer(username).setGroup(prevGroup.getGroupName());
                            ChatHandler.chatConfirmation(sender, username + " demoted to group " + prevGroupString + ".");
                            return true;
                        }
                		else
                		{
                            ChatHandler.chatError(sender, "You must demote to this group manually.");
                		}
                    }
                	else
                	{
                        ChatHandler.chatError(sender, "Player group cannot be found.");
                	}
                }
                else
                {
                    ChatHandler.chatError(sender, prevGroupString + " cannot be found.");
                }
            }
            else
            {
                ChatHandler.chatError(sender, "Player group cannot be found.");
            }
        }
        else
        {
            ChatHandler.chatError(sender, "Player has no prev group set.");
        }
        return false;
    }

    /**
     * Rank down a player
     * @param sender
     * @param username
     * @param group
     * @return
     */
    public static boolean setGroup(ICommandSender sender, String username, String group)
    {
        String newGroupString = group;
        Group newGroup =  Group.getGroup(newGroupString);
        if (newGroupString != null)
        {
            if (newGroup != null)
            {
            	if (newGroup.isPermCommandsDisabled() == false)
                {
                    Player.getPlayer(username).setGroup(newGroup.getGroupName());
                    ChatHandler.chatConfirmation(sender, username + " group set to " + newGroupString + ".");
                    return true;
                }
        		else
        		{
                    ChatHandler.chatError(sender, "You must set to this group manually.");
        		}
            }
            else
            {
                ChatHandler.chatError(sender, "The group " + newGroupString + " cannot be found.");
            }
        }
        else
        {
            ChatHandler.chatError(sender, "New group cannot be found.");
        }
        return false;
    }
}