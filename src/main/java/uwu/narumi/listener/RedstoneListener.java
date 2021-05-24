package uwu.narumi.listener;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import uwu.narumi.Main;
import uwu.narumi.config.Config;
import uwu.narumi.helper.ChatHelper;

public class RedstoneListener implements Listener {

  private final Config config = Main.getInstance().getCustomConfig();
  private final Cache<Chunk, AtomicInteger> changes = Caffeine.newBuilder()
      .expireAfterWrite(config.LIMITER.maxChangesPer, config.LIMITER.maxChangesPerUnit)
      .build();

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onRedstone(BlockRedstoneEvent event) {
    if (config.disableRedstone || (config.canDisableRedstone && config.AUTO.enabled)) {
      event.setNewCurrent(0);
      return;
    }

    if (config.LIMITER.enabled && (event.getBlock().getBlockPower() > 0 || event.getBlock()
        .isBlockPowered())) {
      if (changes.asMap()
          .computeIfAbsent(event.getBlock().getChunk(), ignored -> new AtomicInteger())
          .incrementAndGet() > config.LIMITER.maxChanges) {
        event.setNewCurrent(0);
      }
    }
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    checkAndNotify(event.getPlayer(), event.getClickedBlock());
  }

  @EventHandler
  public void onBlockPLace(BlockPlaceEvent event) {
    checkAndNotify(event.getPlayer(), event.getBlock());
  }

  private void checkAndNotify(Player player, Block block) {
    if (block != null && config.notifyNearestPlayer && (config.disableRedstone || (
        config.canDisableRedstone && config.AUTO.enabled))) {
      String name = block.getType().name();
      //I don't any other idea to make this work on 1.8 - 1.16 without using multi contains if
      if (name.contains("REPEATER") || name.contains("COMPARATOR") || name.contains("REDSTONE")
          || name.contains("PISTON") || name.contains("LEVER") || name.contains("OBSERVER")) {
        player.sendMessage(ChatHelper.fixColors(config.MESSAGES.get("PlayerNotify")));
      }
    }
  }
}
