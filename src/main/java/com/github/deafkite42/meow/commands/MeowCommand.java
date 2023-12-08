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

public class MeowCommand extends BaseCommand{
    private final HashMap<UUID, Long> cooldown;
    public MeowCommand(){
        this.cooldown = new HashMap<>();

    }
    @CommandAlias("meow")
    @Description("Mroww meow meow :3")
    @CommandPermission("meow.playsound.meow")
    public void onMeow(Player player) {
        long timeElapsed;

        if (!this.cooldown.containsKey(player.getUniqueId())) {
            this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
            timeElapsed = -1;
        } else {
            timeElapsed = System.currentTimeMillis() - cooldown.get(player.getUniqueId());
        }

        if (timeElapsed >= (Meow.getInstance().getConfig().getInt("cooldown")) * 1000L || timeElapsed == -1 || player.hasPermission("meow.playsound.cooldownexempt")) {
            Sound sound = (int) Math.round(Math.random() * 1) == 0 ? Sound.ENTITY_CAT_AMBIENT : Sound.ENTITY_CAT_STRAY_AMBIENT;
            player.getWorld().playSound(player, sound, (float) Meow.getInstance().getConfig().getDouble("volume"), (float) ((Math.random() * (1.25 - 0.75)) + 0.75));
            player.sendMessage(Component.text().content("Meow :3").color(TextColor.color(0xF481FE)).decorate(TextDecoration.BOLD));
            this.cooldown.replace(player.getUniqueId(), System.currentTimeMillis());
        } else {
            player.sendMessage(Component.text().content("You must wait before meowing again.").color(NamedTextColor.RED));
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
