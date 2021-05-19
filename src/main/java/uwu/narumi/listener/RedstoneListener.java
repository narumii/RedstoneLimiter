package uwu.narumi.listener;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import uwu.narumi.Main;
import uwu.narumi.config.Config;

public class RedstoneListener implements Listener {

  private final Config config = Main.getInstance().getCustomConfig();
  private final Cache<Chunk, AtomicInteger> changes = Caffeine.newBuilder()
      .expireAfterWrite(1, TimeUnit.SECONDS)
      .build();

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onRedstone(BlockRedstoneEvent event) {
    if (config.disableRedstone || (config.canDisableRedstone && config.autoDisable)) {
      event.setNewCurrent(0);
      return;
    }

    if (event.getBlock().getBlockPower() > 0) {
      if (changes.asMap()
          .computeIfAbsent(event.getBlock().getChunk(), ignored -> new AtomicInteger())
          .incrementAndGet() > config.limit) {
        event.setNewCurrent(0);
      }
    }
  }
}
