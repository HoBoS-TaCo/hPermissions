package hobos_taco.hpermissions.data;

import hobos_taco.hpermissions.HPermissions;
import hobos_taco.hpermissions.util.ChatColours;
import hobos_taco.hpermissions.util.ChatHandler;
import hobos_taco.hpermissions.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

public class Group
{
	private String groupName;
	private String displayName;
	private String chatPrefix;
	private String chatSuffix;
	private String chatFormat;
	private String nextGroup;
	private String prevGroup;
	private boolean permCommandsDisabled;
	private String[] permissions;
	
	public static hobos_taco.hpermissions.data.Group getGroup(String name)
	{
		return HPermissions.groupList.get(name);
	}

	public static void saveGroup(String name)
	{
        Group group = HPermissions.groupList.get(name);
          
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
        HPermissions.groupList.remove(name);

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

            
            HPermissions.groupList.put(name, group);
            
            return true;
        }
	}

	public static void unloadGroup(String name) throws IOException
	{
		saveGroup(name);
		HPermissions.groupList.remove(name);
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
        			ChatHandler.logException(Level.WARNING, "Group " + group + " from " + folder.toString() + " not loaded.");
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
        String[] groups = HPermissions.groupList.keySet().toArray(new String[HPermissions.groupList.keySet().size()]);
        
        for (int i = 0; i < groups.length; i++)
        {
            saveGroup(groups[i]);
        }
    }


    public static void createDefaultGroups()
    {
        Group defaultG = new Group();
        defaultG.setGroupName("Default");
        defaultG.setDisplayName("Default");
        defaultG.setChatPrefix("");
        defaultG.setChatSuffix("");
        defaultG.setNextGroup("Moderator");
        defaultG.setPermCommandsDisabled(false);
        defaultG.setPermissions(new String[] {});
        defaultG.setChatFormat(ChatColours.DARKBLUE + "[Default] <%prefix%username%suffix%> " + ChatColours.RESET);
        HPermissions.groupList.put("Default", defaultG);
        
        Group moderatorG = new Group();
        moderatorG.setGroupName("Moderator");
        moderatorG.setDisplayName("Moderator");
        moderatorG.setChatPrefix("");
        moderatorG.setChatSuffix("");
        moderatorG.setNextGroup("Admin");
        moderatorG.setPrevGroup("Default");
        moderatorG.setPermCommandsDisabled(true);
        moderatorG.setPermissions(new String[] {"hpermissions.reload", "hpermissions.promote", "hpermissions.set", "hpermissions.demote"});
        moderatorG.setChatFormat(ChatColours.GREEN + "[Moderator] <%prefix%username%suffix%> " + ChatColours.RESET);
        HPermissions.groupList.put("Moderator", moderatorG);
        
        Group adminG = new Group();
        adminG.setGroupName("Admin");
        adminG.setDisplayName("Admin");
        adminG.setChatPrefix("");
        adminG.setChatSuffix("");
        adminG.setPrevGroup("Moderator");
        adminG.setPermCommandsDisabled(true);
        adminG.setPermissions(new String[] {"hpermissions.reload", "hpermissions.promote", "hpermissions.set", "hpermissions.demote"});
        adminG.setChatFormat(ChatColours.RED + "[Admin] <%prefix%username%suffix%> " + ChatColours.RESET);
        HPermissions.groupList.put("Admin", adminG);
        
        saveGroup("Default");
        saveGroup("Moderator");
        saveGroup("Admin");
    }

	/**
	 * @return the groupName
	 */
	public String getGroupName()
	{
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	/**
	 * @return the chatPrefix
	 */
	public String getChatPrefix()
	{
		return chatPrefix;
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
		return chatSuffix;
	}

	/**
	 * @param chatSuffix the chatSuffix to set
	 */
	public void setChatSuffix(String chatSuffix)
	{
		this.chatSuffix = chatSuffix;
	}

	/**
	 * @return the nextGroup
	 */
	public String getNextGroup()
	{
		return nextGroup;
	}

	/**
	 * @param nextGroup the nextGroup to set
	 */
	public void setNextGroup(String nextGroup)
	{
		this.nextGroup = nextGroup;
	}

	/**
	 * @return the prevGroup
	 */
	public String getPrevGroup()
	{
		return prevGroup;
	}

	/**
	 * @param prevGroup the prevGroup to set
	 */
	public void setPrevGroup(String prevGroup)
	{
		this.prevGroup = prevGroup;
	}

	/**
	 * @return the permCommandsDisabled
	 */
	public boolean isPermCommandsDisabled()
	{
		return permCommandsDisabled;
	}

	/**
	 * @param permCommandsDisabled the permCommandsDisabled to set
	 */
	public void setPermCommandsDisabled(boolean permCommandsDisabled)
	{
		this.permCommandsDisabled = permCommandsDisabled;
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
	 * @return the chatFormat
	 */
	public String getChatFormat()
	{
		return chatFormat;
	}

	/**
	 * @param chatFormat the chatFormat to set
	 */
	public void setChatFormat(String chatFormat)
	{
		this.chatFormat = chatFormat;
	}
}