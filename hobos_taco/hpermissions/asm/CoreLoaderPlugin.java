package hobos_taco.hpermissions.asm;

import hobos_taco.hpermissions.util.Util;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

/*
 * Don't let any access transformer stuff accidentally modify our classes
 * A list of package prefixes for FML to ignore
 */
@TransformerExclusions({"hobos_taco.hpermissions.asm"})
@MCVersion(Util.MCVERSION)
public class CoreLoaderPlugin implements IFMLLoadingPlugin
{
    /*
     * Use if you want to download libraries. Returns a list of classes that implement the ILibrarySet interface
     * eg return new String[] { "tutorial.asm.downloaders.DownloadUsefulLibrary " };
     */
	@Override
    public String[] getLibraryRequestClass()
    {
       return null;
    }

    /*
     * The class(es) that do(es) the transforming. Needs to implement IClassTransformer in some way
     */
    @Override
    public String[] getASMTransformerClass()
    {
        return null;
    }

    /*
     * The class that acts similarly to the @Mod annotation.
     */
    @Override
    public String getModContainerClass()
    {
        return "hobos_taco.hpermissions.CoreModContainer";
    }

    /*
     * If you want to do stuff BEFORE minecraft starts, but after your mod is loaded.
     */
    @Override
    public String getSetupClass()
    {
        return null;
    }

    /*
     * Gives the mod coremod data if it wants it.
     */
    @Override
    public void injectData(Map<String, Object> data)
    {
    }
}
