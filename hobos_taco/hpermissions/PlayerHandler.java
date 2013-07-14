package hobos_taco.hpermissions;

import hobos_taco.hpermissions.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerHandler implements IPlayerTracker
{
	@Override
	public void onPlayerLogin(EntityPlayer player)
	{
	    PlayerData.loadPlayer(player.username);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player)
	{
		PlayerData.unloadPlayer(player.username);
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player)
	{
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player)
	{
	}
}
