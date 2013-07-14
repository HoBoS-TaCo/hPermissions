package hobos_taco.hpermissions.data;

import hobos_taco.hpermissions.CoreModContainer;
import hobos_taco.hpermissions.util.Config;
import hobos_taco.hpermissions.util.PermLog;
import hobos_taco.hpermissions.util.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import net.minecraft.server.MinecraftServer;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

public class PlayerData
{
	public static Player getPlayer(String username)
	{
        return CoreModContainer.playerList.get(username);
	}

	public static void savePlayer(String username)
	{
	    Player player = CoreModContainer.playerList.get(username);
        
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
	    CoreModContainer.playerList.remove(username);

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
	        CoreModContainer.playerList.put(username, player);
	    }
	}

	public static void unloadPlayer(String username)
	{
	    CoreModContainer.playerList.remove(username);
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
        player.prefix = "";
        player.suffix = "";
        if (GroupData.getGroup(Config.defaultGroup) != null)
        {
            player.group = Config.defaultGroup;
        }
        else
        {
            PermLog.error("Error: Default group " + Config.defaultGroup + " has not been loaded from the Groups folder.");
        }
        
        player.allaccess = false;
        
        CoreModContainer.playerList.put(username, player);
	}
}