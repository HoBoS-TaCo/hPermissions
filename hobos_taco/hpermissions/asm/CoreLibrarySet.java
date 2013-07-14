package hobos_taco.hpermissions.asm;

import cpw.mods.fml.relauncher.ILibrarySet;

public class CoreLibrarySet implements ILibrarySet
{
    @Override
    public String[] getHashes() 
    {
        return new String[] {"52bbb25465e93308038a6a7401899a06407b98cd"};
    }

    @Override
    public String[] getLibraries() 
    {
        return new String[] {"yamlbeans-1.06.jar"};
    }

    @Override
    public String getRootURL() 
    {
        return "http://repo1.maven.org/maven2/com/esotericsoftware/yamlbeans/yamlbeans/1.06/%s";
    }
}
