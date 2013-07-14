package hobos_taco.hpermissions;

import hobos_taco.hpermissions.data.GroupData;
import hobos_taco.hpermissions.data.PlayerData;
import hobos_taco.hpermissions.util.PermLog;
import hobos_taco.hpermissions.util.Util;
import java.util.ArrayList;
import java.util.List;

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
        PermLog.debug("username == " + username);
        PermLog.debug("perm == " + perm);

        String group = PlayerData.getPlayer(username).group;
        PermLog.debug("group == " + group);

        String[] pPerms = PlayerData.getPlayer(username).perms;
        String[] gPerms = GroupData.getGroup(group).perms;
        
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
            PermLog.debug("pList.contains(perm)");
            return true;
        }
        else
        {
            PermLog.debug("!pList.contains(perm)");
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
        PermLog.debug("hasPermission");
        if (PlayerData.getPlayer(username).allaccess == true)
        {
            PermLog.debug("allaccess == true");
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
}