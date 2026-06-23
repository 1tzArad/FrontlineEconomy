package xyz.ItzArad.frontlineEconomy.core.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import xyz.ItzArad.frontlineEconomy.core.FrontlineEconomy;

@UtilityClass
public class SoundUtils {

    public void playSenderSound(Player player){
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 1.3f);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.5f);
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.2f);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
    }
    public void playReceiverSound(Player to) {
        to.playSound(to.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.5f);
        to.playSound(to.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.2f);
        Bukkit.getScheduler().runTaskLater(FrontlineEconomy.getInstance(), () ->
                to.playSound(to.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 1.3f), 2L);

        Bukkit.getScheduler().runTaskLater(FrontlineEconomy.getInstance(), () ->
                to.playSound(to.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f), 4L);
    }

}
