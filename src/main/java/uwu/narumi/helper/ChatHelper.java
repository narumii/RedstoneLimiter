package uwu.narumi.helper;

import net.md_5.bungee.api.ChatColor;

public class ChatHelper {

  public static String fixColors(String string) {
    return ChatColor.translateAlternateColorCodes('&', string);
  }
}
