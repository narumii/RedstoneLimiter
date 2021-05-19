package uwu.narumi.command;

import java.util.Collections;
import java.util.Locale;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import uwu.narumi.Main;
import uwu.narumi.config.Config;

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
        " §8» §7Usage: §c/redstone <limit/tps/state/auto> <value>",
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
    if ((command.equals("limit") || command.equals("tps")) && !numberPattern.matcher(args[1]).matches()) {
      sender.sendMessage(" §8» §7Usage: §c/redstone <limit/tps> <number>");
      return false;
    }

    switch (command) {
      case "limit": {
        int amount = Integer.parseInt(args[1]);
        config.limit = amount;
        sender.sendMessage(" §8» §7Max redstone changes per second per chunk was set to: §c" + amount);
        break;
      }
      case "tps": {
        int amount = Integer.parseInt(args[1]);
        config.limit = amount;
        sender.sendMessage(" §8» §7Auto disable min tps was set to: §c" + amount);
        break;
      }
      case "state": {
        boolean state = Boolean.parseBoolean(args[1]);
        config.disableRedstone = !state;
        Bukkit.broadcastMessage(" §8» §c" + (state ? "Enabled" : "Disabled") + " §7redstone");
        break;
      }
      case "auto": {
        boolean state = Boolean.parseBoolean(args[1]);
        config.autoDisable = state;
        sender.sendMessage(" §8» §c" + (state ? "Enabled" : "Disabled") + " §7auto redstone disabler");
        break;
      }
      default: sender.sendMessage(getUsage());
    }

    return true;
  }
}
