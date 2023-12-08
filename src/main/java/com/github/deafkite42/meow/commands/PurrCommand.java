package com.github.deafkite42.meow.commands;

import com.github.deafkite42.meow.Meow;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.BaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Sound;

import java.lang.Math;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.UUID;

public class PurrCommand extends BaseCommand{
    private final HashMap<UUID, Long> cooldown;
    public PurrCommand(){
        this.cooldown = new HashMap<>();

    }
    @CommandAlias("purr")
    @Description("Purrrr :3 ")
    @CommandPermission("meow.playsound.purr")
    public void onPurr(Player player) {
        long timeElapsed;

        if (!this.cooldown.containsKey(player.getUniqueId())) {
            this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
            timeElapsed = -1;
        } else {
            timeElapsed = System.currentTimeMillis() - cooldown.get(player.getUniqueId());
        }

        if (timeElapsed >= (Meow.getInstance().getConfig().getInt("cooldown")) * 1000L || timeElapsed == -1 || player.hasPermission("meow.playsound.cooldownexempt")) {
            player.getWorld().playSound(player, Sound.ENTITY_CAT_PURR, (float) Meow.getInstance().getConfig().getDouble("volume"), (float) ((Math.random() * (1.25 - 0.75)) + 0.75));
            player.sendMessage(Component.text().content("Purr :3").color(TextColor.color(0xF481FE)).decorate(TextDecoration.BOLD));
            this.cooldown.replace(player.getUniqueId(), System.currentTimeMillis());
        } else {
            player.sendMessage(Component.text().content("You must wait before purring again.").color(NamedTextColor.RED));
        }

        List<UUID> playersOnlineUUIDs = Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toList());
        Set<UUID> cooldownKeyset = cooldown.keySet();
        for (UUID uuid: cooldownKeyset) {
            if(!playersOnlineUUIDs.contains(uuid)) {
                cooldown.remove(uuid);
            }
            Meow.getInstance().getLogger().info(String.valueOf((uuid)));
        }
    }
}