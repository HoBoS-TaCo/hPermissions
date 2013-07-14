package hobos_taco.hpermissions;

import hobos_taco.hpermissions.data.Group;
import hobos_taco.hpermissions.data.GroupData;
import hobos_taco.hpermissions.data.PlayerData;
import hobos_taco.hpermissions.util.PermLog;

public class Rank
{
    /**
     * Promote a player
     * @param username - player username
     * @return true if successful
     */
    public static boolean promote(String username)
    {
        PermLog.debug("promote " + username);
        String playerGroup = PlayerData.getPlayer(username).group;

        if (playerGroup != null)
        {
            PermLog.debug("playerGroup != null");
            PlayerData.getPlayer(username).group = GroupData.getGroup(playerGroup).nextGroup;
            return true;
        }
        else
        {
            PermLog.debug("no group for player");
            return false;
        }
    }
    
    /**
     * Demote a player
     * @param username - player username
     * @return true if successful
     */
    public static boolean demote(String username)
    {
        String playerGroup = PlayerData.getPlayer(username).group;

        //null check
        if (playerGroup != null)
        {
            PlayerData.getPlayer(username).group = GroupData.getGroup(playerGroup).prevGroup;
            return true;
        }
        else
        {
            //TODO no group for player
            return false;
        }
    }

    /**
     * Rank down a player
     * @param username - player username
     * @return true if successful
     */
    public static boolean setGroup(String username, String group)
    {
        String playerGroup = PlayerData.getPlayer(username).group;

        Group newGroup = GroupData.getGroup(group);
        
        if (newGroup != null)
        {
            PlayerData.getPlayer(username).group = newGroup.name;
            return true;
        }
        else
        {
            return false;
        }
    }
}
