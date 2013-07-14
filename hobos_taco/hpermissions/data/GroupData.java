package hobos_taco.hpermissions.data;

import hobos_taco.hpermissions.CoreModContainer;
import hobos_taco.hpermissions.util.PermLog;
import hobos_taco.hpermissions.util.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

public class GroupData
{
	public static hobos_taco.hpermissions.data.Group getGroup(String name)
	{
		return CoreModContainer.groupList.get(name);
	}

	public static void saveGroup(String name)
	{
        Group group = CoreModContainer.groupList.get(name);
          
        File file = new File(Util.HPERMFOLDER, "Groups");
        file.mkdir();
        Util.HPERMGROUPFOLDER = file;

        File groupFile = new File(Util.HPERMGROUPFOLDER, name + ".yml");

        try
        {
            YamlWriter writer = new YamlWriter(new FileWriter(groupFile));
            writer.getConfig().setClassTag("Group", Group.class);
            writer.write(group);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}

	public static boolean loadGroup(String name)
	{
        CoreModContainer.groupList.remove(name);

        File file = new File(Util.HPERMFOLDER, "Groups");
        file.mkdir();
        Util.HPERMGROUPFOLDER = file;

        File groupFile = new File(Util.HPERMGROUPFOLDER, name + ".yml");

        if (!groupFile.exists())
        {
            return false;
        }
        else
        {
            Group group = null;
            YamlReader reader = null;
            
            try
            {
                reader = new YamlReader(new FileReader(groupFile));
                reader.getConfig().setClassTag("Group", Group.class);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            try
            {
                group = reader.read(Group.class);
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

            
            CoreModContainer.groupList.put(name, group);
            
            return true;
        }
	}

	public static void unloadGroup(String name) throws IOException
	{
		saveGroup(name);
		CoreModContainer.groupList.remove(name);
	}

	public static void loadAllGroups()
	{
        File folder = Util.HPERMGROUPFOLDER;
        File[] listOfFiles = folder.listFiles();
        Util.GROUPSLOADED = 0;
        
        for (int i = 0; i < listOfFiles.length; i++)
        {
        	File file = listOfFiles[i];
        	if (file.isFile() && file.getName().endsWith(".yml"))
        	{
        		String group = file.getName();
        		int pos = group.lastIndexOf(".");
        		if (pos > 0)
        		{
        			group = group.substring(0, pos);
        		}
        		else
        		{
        			PermLog.error("Group " + group + " from " + folder.toString() + " not loaded.");
        			return;
        		}
        		
        		if (loadGroup(group) == true)
        		{
        		    Util.GROUPSLOADED++;
        		}
        	}
        }
	}

    public static void saveAllGroups()
    {
        File folder = Util.HPERMGROUPFOLDER;
        String[] groups = CoreModContainer.groupList.keySet().toArray(new String[CoreModContainer.groupList.keySet().size()]);
        
        for (int i = 0; i < groups.length; i++)
        {
            saveGroup(groups[i]);
        }
    }


    public static void createDefaultGroups()
    {
        Group defaultG = new Group();
        defaultG.name = "Default";
        defaultG.displayName = "Default";
        defaultG.name = "Default";
        defaultG.prefix = "Newbie";
        defaultG.suffix = "";
        defaultG.nextGroup = "Moderator";
        defaultG.perms = new String[] {};
        CoreModContainer.groupList.put("Default", defaultG);
        
        Group moderatorG = new Group();
        moderatorG.name = "Moderator";
        moderatorG.displayName = "Moderator";
        moderatorG.name = "Moderator";
        moderatorG.prefix = "Moderator";
        moderatorG.suffix = "";
        moderatorG.nextGroup = "Admin";
        moderatorG.prevGroup = "Default";
        moderatorG.perms = new String[] {"hpermissions.reload", "hpermissions.promote", "hpermissions.addspecial", "hpermissions.demote"};
        CoreModContainer.groupList.put("Moderator", moderatorG);
        
        Group adminG = new Group();
        adminG.name = "Admin";
        adminG.displayName = "Admin";
        adminG.name = "Admin";
        adminG.prefix = "Admin";
        adminG.suffix = "";
        adminG.prevGroup = "Moderator";
        adminG.perms = new String[] {"hpermissions.reload", "hpermissions.promote", "hpermissions.addspecial", "hpermissions.demote"};
        CoreModContainer.groupList.put("Admin", adminG);
        
        saveGroup("Default");
        saveGroup("Moderator");
        saveGroup("Admin");
    }
}