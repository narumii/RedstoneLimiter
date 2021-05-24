package uwu.narumi.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import org.bukkit.Bukkit;

public class ConfigManager {

  private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  private final File configFile;

  public ConfigManager(File dir) {
    configFile = new File(dir, "config.json");
  }

  public Config loadConfig() {
    try (Reader reader = new FileReader(configFile)) {
      return GSON.fromJson(reader, Config.class);
    } catch (Exception e) {
      Bukkit.getLogger().severe("Can't load config from file, using default config.");
    }

    return new Config();
  }

  public void saveConfig(Config config) {
    try (Writer writer = new FileWriter(configFile)) {
      GSON.toJson(config, writer);
    } catch (Exception e) {
      Bukkit.getLogger().severe("Can't save plugin config.");
    }
  }
}
