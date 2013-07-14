package hobos_taco.hpermissions.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util
{
	public static final String ID = "hPermissions";
	public static final String NAME = "hPermissions";
	public static final String VERSION = "0.1.0";
	public static String WEBVERSION;
	public static final String MCVERSION = "1.6.2";
	public static final String DESC = "hPermissions is a server-side permissions mod for a Forge-modded Minecraft server.";
	public static final List<String> AUTHORLIST = Arrays.asList("HoBoS_TaCo");
	public static final String URL = "https://github.com/HoBoS-TaCo/hPermissions/wiki";
    public static final String CREDITS = "YamlBeans for Yaml management.";
	
	public static File HPERMFOLDER;
	public static File HPERMPLAYERFOLDER;
	public static File HPERMGROUPFOLDER;
    
	public static int GROUPSLOADED;
	
	public static ArrayList<String> arrayToArrayList(String[] array)
	{
	    ArrayList<String> arrayList = new ArrayList<String>();
	    for (int i = 0; i < array.length;)
	    {
	        arrayList.add(array[i]);
	        i++;
	    }
	    
        return arrayList;
	}
	
	public static String[] arrayListToArray(ArrayList<String> arrayList)
    {
        return arrayList.toArray(new String[arrayList.size()]);
    }
}
