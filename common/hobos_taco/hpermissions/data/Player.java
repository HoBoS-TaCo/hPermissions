package hobos_taco.hpermissions.data;

import hobos_taco.hpermissions.HPermissions;
import hobos_taco.hpermissions.util.ChatHandler;
import hobos_taco.hpermissions.util.Config;
import hobos_taco.hpermissions.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.server.MinecraftServer;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

public class Player
{
	private String username;
    private String chatPrefix;
    private String chatSuffix;
	private String group;
	private String[] permissions;
	private boolean allAccess;
	
	public static Player getPlayer(String username)
	{
        return HPermissions.playerList.get(username);
	}

	public static void savePlayer(String username)
	{
	    Player player = HPermissions.playerList.get(username);
        
        File file = new File(Util.HPERMFOLDER, "Players");
        file.mkdir();
        Util.HPERMPLAYERFOLDER = file;

        File playerFile = new File(Util.HPERMPLAYERFOLDER, username + ".yml");

        try
        {
            YamlWriter writer = new YamlWriter(new FileWriter(playerFile));
            writer.getConfig().setClassTag("Player", Player.class);
            writer.write(player);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}

	public static void loadPlayer(String username)
	{
	    HPermissions.playerList.remove(username);

	    File file = new File(Util.HPERMFOLDER, "Players");
        file.mkdir();
	    Util.HPERMPLAYERFOLDER = file;

	    File playerFile = new File(Util.HPERMPLAYERFOLDER, username + ".yml");

	    if (!playerFile.exists())
	    {
	        createPlayer(username);
	                
	        savePlayer(username);
	    }
	    else
	    {
            Player player = null;
            YamlReader reader = null;
            
            try
            {
                reader = new YamlReader(new FileReader(playerFile));
                reader.getConfig().setClassTag("Player", Player.class);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            try
            {
                player = reader.read(Player.class);
            }
            catch (YamlException e)
            {
                e.printStackTrace();
            }
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
	        HPermissions.playerList.put(username, player);
	    }
	}

	public static void unloadPlayer(String username)
	{
	    HPermissions.playerList.remove(username);
	}
	
	public static void loadAllPlayers()
    {
        String[] players = MinecraftServer.getServer().getAllUsernames();
        
        for (int i = 0; i < players.length;)
        {
            loadPlayer(players[i]);
            i++;
        }
    }
	
    public static void saveAllPlayers()
    {
        String[] players = MinecraftServer.getServer().getAllUsernames();
        
        for (int i = 0; i < players.length;)
        {
            savePlayer(players[i]);
            i++;
        }
    }

	public static void createPlayer(String username)
	{
        Player player = new Player();
        player.username = username;
        player.setChatPrefix("");
        player.setChatSuffix("");
        if (Group.getGroup(Config.defaultGroup) != null)
        {
            player.group = Config.defaultGroup;
        }
        else
        {
        	ChatHandler.logException(Level.SEVERE, "Default group cannot be found or loaded.");
        }
        
        player.setAllAccess(false);
        
        HPermissions.playerList.put(username, player);
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return the chatPrefix
	 */
	public String getChatPrefix()
	{
		String string = "";
		if (chatPrefix != null)
		{
			string = chatPrefix;
		}
		return string;
	}

	/**
	 * @param chatPrefix the chatPrefix to set
	 */
	public void setChatPrefix(String chatPrefix)
	{
		this.chatPrefix = chatPrefix;
	}

	/**
	 * @return the chatSuffix
	 */
	public String getChatSuffix()
	{
		String string = "";
		if (chatSuffix != null)
		{
			string = chatSuffix;
		}
		return string;
	}

	/**
	 * @param chatSuffix the chatSuffix to set
	 */
	public void setChatSuffix(String chatSuffix)
	{
		this.chatSuffix = chatSuffix;
	}

	/**
	 * @return the group
	 */
	public String getGroup()
	{
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group)
	{
		this.group = group;
	}

	/**
	 * @return the permissions
	 */
	public String[] getPermissions()
	{
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(String[] permissions)
	{
		this.permissions = permissions;
	}

	/**
	 * @return the allAccess
	 */
	public boolean isAllAccess()
	{
		return allAccess;
	}

	/**
	 * @param allAccess the allAccess to set
	 */
	public void setAllAccess(boolean allAccess)
	{
		this.allAccess = allAccess;
	}
}