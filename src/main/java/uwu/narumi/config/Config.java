package uwu.narumi.config;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Config {

  @SerializedName("RedstoneChangeLimiter")
  public RedstoneChangeLimiter LIMITER = new RedstoneChangeLimiter();

  @SerializedName("AutoDisable")
  public AutoDisable AUTO = new AutoDisable();

  public boolean disableRedstone;
  public boolean notifyNearestPlayer;
  public boolean canDisableRedstone; //for autodisable;

  @SerializedName("Messages")
  public Map<String, String> MESSAGES = new HashMap<String, String>() {{
    //Task Messages
    put("DisabledRedstone", " §8» §cDisabled redstone because of lags");
    put("EnabledRedstone", " §8» §cRedstone has been enabled because lags gone");

    //Command Usages
    put("CommandUsage", " §8» §7Usage: §c/redstone <limit/tps/state/auto> <value>");
    put("NumberCommandUsage", " §8» §7Usage: §c/redstone <maxchanges/tps> <number>");

    //Player Notify
    put("PlayerNotify", " §8» §cSorry redstone is currently disabled");

    //Command Arguments Messages
    put("maxchanges", " §8» §7Max redstone changes was set to: §c {amount}");
    put("tps", " §8» §7Auto disable min tps was set to: §c {amount}");
    put("state", " §8» §c{state} §7redstone");
    put("auto", " §8» §c{state} §7auto redstone disabler");
  }};

  public class RedstoneChangeLimiter {

    public boolean enabled = true;
    public int maxChanges = 600;
    public int maxChangesPer = 1;
    public TimeUnit maxChangesPerUnit = TimeUnit.SECONDS;
  }

  public class AutoDisable {

    public boolean enabled = true;
    public int tpsTrigger = 16;
  }
}
