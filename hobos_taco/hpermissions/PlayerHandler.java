package hobos_taco.hpermissions;

import hobos_taco.hpermissions.data.Player;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerHandler implements IPlayerTracker
{
	@Override
	public void onPlayerLogin(EntityPlayer player)
	{
	    Player.loadPlayer(player.username);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player)
	{
		Player.unloadPlayer(player.username);
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
