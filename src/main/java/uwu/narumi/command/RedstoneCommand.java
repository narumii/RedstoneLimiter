package uwu.narumi.command;

import java.util.Collections;
import java.util.Locale;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import uwu.narumi.Main;
import uwu.narumi.config.Config;
import uwu.narumi.helper.ChatHelper;

public class RedstoneCommand extends Command {

  /*
  Yes this class looks like shit
   */

  private static final Pattern numberPattern = Pattern.compile("\\d+");

  private final Config config = Main.getInstance().getCustomConfig();

  public RedstoneCommand() {
    super(
        "redstone",
        "Command for change redstonelimiter settings",
        ChatHelper.fixColors(Main.getInstance().getCustomConfig().MESSAGES.get("CommandUsage")),
        Collections.singletonList("redstonelimiter")
    );
  }

  @Override
  public boolean execute(CommandSender sender, String commandName, String[] args) {
    if (args.length < 2) {
      sender.sendMessage(getUsage());
      return false;
    }

    String command = args[0].toLowerCase(Locale.ROOT);
    if ((command.equals("maxchanges") || command.equals("tps")) && !numberPattern.matcher(args[1])
        .matches()) {
      sender.sendMessage(ChatHelper.fixColors(config.MESSAGES.get("NumberCommandUsage")));
      return false;
    }

    String message = ChatHelper.fixColors(config.MESSAGES.get(command));
    switch (command) {
      case "maxchanges": {
        int amount = Integer.parseInt(args[1]);
        config.LIMITER.maxChanges = amount;
        sender.sendMessage(message.replace("{amount}", args[1]));
        break;
      }
      case "tps": {
        int amount = Integer.parseInt(args[1]);
        config.AUTO.tpsTrigger = amount;
        sender.sendMessage(message.replace("{amount}", args[1]));
        break;
      }
      case "state": {
        boolean state = Boolean.parseBoolean(args[1]);
        config.disableRedstone = !state;
        Bukkit.broadcastMessage(message.replace("{state}", (state ? "Enabled" : "Disabled")));
        break;
      }
      case "auto": {
        boolean state = Boolean.parseBoolean(args[1]);
        config.AUTO.enabled = state;
        sender.sendMessage(message.replace("{state}", (state ? "Enabled" : "Disabled")));
        break;
      }
      default:
        sender.sendMessage(ChatHelper.fixColors(config.MESSAGES.get("CommandUsage")));
    }

    return true;
  }
}
