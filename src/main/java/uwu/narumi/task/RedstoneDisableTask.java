package uwu.narumi.task;

import org.bukkit.Bukkit;
import uwu.narumi.Main;
import uwu.narumi.config.Config;
import uwu.narumi.helper.ChatHelper;
import uwu.narumi.helper.ReflectionHelper;

public class RedstoneDisableTask implements Runnable {

  private final Config config = Main.getInstance().getCustomConfig();

  @Override
  public void run() {
    int tps = (int) Math.min(Math.round(ReflectionHelper.getTPS()[0] * 100.0) / 100.0, 20.0);
    if (tps < config.AUTO.tpsTrigger && !config.canDisableRedstone) {
      Bukkit.broadcastMessage(ChatHelper.fixColors(config.MESSAGES.get("DisabledRedstone")));
      config.canDisableRedstone = true;
    } else if (tps > config.AUTO.tpsTrigger && config.canDisableRedstone) {
      Bukkit.broadcastMessage(ChatHelper.fixColors(config.MESSAGES.get("EnabledRedstone")));
      config.canDisableRedstone = false;
    }
  }
}
