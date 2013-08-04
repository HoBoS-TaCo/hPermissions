package hobos_taco.hpermissions.util;

import hobos_taco.hpermissions.data.Group;
import hobos_taco.hpermissions.data.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.util.ChatMessageComponent;
import cpw.mods.fml.common.FMLCommonHandler;

public final class ChatHandler
{
    public static Logger log;
    
	/**
	 * outputs a message in red text to the chat box of the given player.
	 * @param msg
	 * the message to be chatted
	 * @param player
	 * player to chat to.
	 */
	public static void chatError(ICommandSender sender, String msg)
	{
		if (sender instanceof EntityPlayer)
		{
			sender.sendChatToPlayer(ChatMessageComponent.func_111066_d(ChatColours.RED + msg(msg)));
		}
		else
		{
			sender.sendChatToPlayer(ChatMessageComponent.func_111066_d(msg(msg)));
		}
	}
	
	/**
	 * outputs a message in bright green to the chat box of the given player.
	 * @param msg
	 * the message to be chatted
	 * @param player
	 * player to chat to.
	 */
	public static void chatConfirmation(ICommandSender sender, String msg)
	{
		if (sender instanceof EntityPlayer)
		{
			sender.sendChatToPlayer(ChatMessageComponent.func_111066_d(ChatColours.GREEN + msg(msg)));
		}
		else
		{
			sender.sendChatToPlayer(ChatMessageComponent.func_111066_d(msg(msg)));
		}
	}
	
	/**
	 * outputs a message in yellow to the chat box of the given player.
	 * @param msg
	 * the message to be chatted
	 * @param player
	 * player to chat to.
	 */
	public static void chatWarning(ICommandSender sender, String msg)
	{
		if (sender instanceof EntityPlayer)
		{
			sender.sendChatToPlayer(ChatMessageComponent.func_111066_d(ChatColours.YELLOW + msg(msg)));
		}
		else
		{
			sender.sendChatToPlayer(ChatMessageComponent.func_111066_d(msg(msg)));
		}
	}
	
	/**
	 * Use this to throw errors that can continue without crashing the server.
	 * @param level
	 * @param message
	 * @param error
	 */
	public static void logException(Level level, String message, Throwable error)
	{
		log.log(level, message, error);
	}
	
	/**
	 * Use this to throw errors that can continue without crashing the server.
	 * @param level
	 * @param message
	 */
	public static void logException(Level level, String message)
	{
		log.log(level, message);
	}
	
	/**
	 * outputs a string to the console if Config.debug = true
	 * @param msg
	 * message to be outputted
	 */
	public static void logDebug(String msg)
	{
		if (Config.debug)
		{
			log.info("[DEBUG] " + msg);
		}
	}
	
	/**
	 * outputs a string to the console if Config.debug = true
	 * @param msg
	 * message to be outputted
	 */
	public static void logInfo(String msg)
	{
		log.info(msg);
	}
	
	public static String msg(String message)
	{
		char[] b = message.toCharArray();
		for (int i = 0; i < b.length - 1; i++)
		{
			if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1)
			{
				b[i] = '\u00a7';
				b[i + 1] = Character.toLowerCase(b[i + 1]);
			}
		}
		return new String(b);
	}
	
	public static void chatMessage(String username, String message)
    {        
		Player player = Player.getPlayer(username);
		String name = username;	
		
        String prefix = player.getChatPrefix();
        String suffix = player.getChatSuffix();
        
        String chatFormat = Group.getGroup(player.getGroup()).getChatFormat();
        
        String[] chat = chatFormat.split("%");
        StringBuffer result = new StringBuffer();

        for (String section : chat)
        {
        	if (section.matches("username"))
        	{
        		result.append(name);
        	}
        	else if (section.matches("prefix"))
        	{
        		result.append(prefix);
        	}
        	else if (section.matches("suffix"))
        	{
        		result.append(suffix);
        	}
        	else
        	{
        		result.append(section);
        	}
        }
        
        result.append(message);
        String output = result.toString();
        
        FMLCommonHandler.instance().getMinecraftServerInstance().sendChatToPlayer(ChatMessageComponent.func_111066_d(output));
        FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(ChatMessageComponent.func_111066_d(output), false));
    }
}