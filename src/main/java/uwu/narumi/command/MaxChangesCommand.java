package uwu.narumi.command;

import java.util.Arrays;
import java.util.regex.Pattern;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import uwu.narumi.Main;

public class MaxChangesCommand extends Command {

  private static final Pattern numberPattern = Pattern.compile("\\d+");

  public MaxChangesCommand() {
    super(
        "maxchanges",
        "Allows you to change max redstone changes per second per chunk",
        " §8» §7Usage: §f/maxchanges §c<number>",
        Arrays.asList("setchanges", "changes", "limitredstone")
    );
  }

  @Override
  public boolean execute(CommandSender commandSender, String s, String[] args) {
    if (args.length < 1 || !numberPattern.matcher(args[0]).matches()) {
      commandSender.sendMessage(getUsage());
      return false;
    }

    int amount = Integer.parseInt(args[0]);
    Main.getInstance().setMaxChanges(amount);
    commandSender
        .sendMessage(" §8» §7Max redstone changes per second per chunk was set to: §c" + amount);
    return true;
  }
}
