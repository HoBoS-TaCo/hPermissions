package hobos_taco.hpermissions.util;

import hobos_taco.hpermissions.CoreModContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

public class Config 
{	
	public static boolean debug;
	public static String defaultGroup;

	public static void saveConfig()
	{
        File configFile = new File(Util.HPERMFOLDER, "Config.yml");

        try
        {
            YamlWriter writer = new YamlWriter(new FileWriter(configFile));
            writer.getConfig().setClassTag("Config", Config.class);
            writer.write(CoreModContainer.config);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}

	public static void loadConfig()
	{
        File configFile = new File(Util.HPERMFOLDER, "Config.yml");

	    if (!configFile.exists())
	    {
	        createConfig();
	                
	        saveConfig();
	    }
	    else
	    {
	    	Config config = null;
            YamlReader reader = null;
            
            try
            {
                reader = new YamlReader(new FileReader(configFile));
                reader.getConfig().setClassTag("Config", Config.class);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            try
            {
            	config = reader.read(Config.class);
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
            CoreModContainer.config = config;
	    }
	}

	public static void createConfig()
	{
		Config config = new Config();
		config.debug = false;
		config.defaultGroup = "Default";
		CoreModContainer.config = config;
	}
}
