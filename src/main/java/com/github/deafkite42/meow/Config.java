package com.github.deafkite42.meow;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;

public class Config {
  public static void init(FileConfiguration config) {
      config.addDefault("volume", 10); config.setInlineComments("volume", Collections.singletonList("Volume to play sounds at"));
      config.addDefault("cooldown", 10); config.setInlineComments("cooldown", Collections.singletonList("Cooldown players must wait before meowing/purring again "));

      config.options().copyDefaults(true);
  }
}

