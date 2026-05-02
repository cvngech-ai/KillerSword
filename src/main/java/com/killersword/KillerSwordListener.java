papackage com.killersword;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class KillerSwordListener implements Listener {

    private final KillerSwordPlugin plugin;
    private final Set<UUID> activeParticleTask = new HashSet<>();

    public KillerSwordListener(KillerSwordPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());

        if (KillerSwordItem.isKillerSword(newItem)) {
            applyKillerEffects(player);
        }

        if (KillerSwordItem.isKillerSword(oldItem)) {
            removeKillerEffects(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (KillerSwordItem.isKillerSword(heldItem)) {
            applyKillerEffects(player);
        }
    }

    private void applyKillerEffects(Player player) {
        player.addPotionEffect(new PotionEffect(
            PotionEffectType.INCREASE_DAMAGE,
            99999,
            1,
            false,
            true,
            true
        ));

        player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.5f, 1.5f);
        startParticles(player);

        player.sendMessage(
            org.bukkit.ChatColor.AQUA + "" + org.bukkit.ChatColor.BOLD +
            "⚔ Killer Sword equipped! " +
            org.bukkit.ChatColor.GRAY + "Strength II active!"
        );
    }

    private void removeKillerEffects(Player player) {
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        activeParticleTask.remove(player.getUniqueId());

        player.sendMessage(
            org.bukkit.ChatColor.RED + "⚔ Killer Sword unequipped. Strength removed."
        );
    }

    private void startParticles(Player player) {
        UUID uuid = player.getUniqueId();
        activeParticleTask.add(uuid);

        new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                if (!player.isOnline() || !activeParticleTask.contains(uuid)) {
                    this.cancel();
                    return;
                }

                ItemStack heldItem = player.getInventory().getItemInMainHand();
                if (!KillerSwordItem.isKillerSword(heldItem)) {
                    activeParticleTask.remove(uuid);
                    this.cancel();
                    return;
                }

                Location loc = player.getLocation().add(0, 1, 0);

                double angle = tick * 0.3;
                double radius = 0.8;
                double x = Math.cos(angle) * radius;
                double z = Math.sin(angle) * radius;

                loc.getWorld().spawnParticle(
                    Particle.REDSTONE,
                    loc.clone().add(x, 0, z),
                    1, 0, 0, 0, 0,
                    new Particle.DustOptions(Color.fromRGB(0, 150, 255), 1.2f)
                );

                loc.getWorld().spawnParticle(
                    Particle.REDSTONE,
                    loc.clone().add(-x, 0.3, -z),
                    1, 0, 0, 0, 0,
                    new Particle.DustOptions(Color.fromRGB(0, 80, 255), 1.0f)
                );

                if (tick % 20 == 0) {
                    loc.getWorld().spawnParticle(
                        Particle.WATER_SPLASH,
                        loc,
                        10, 0.5, 0.5, 0.5, 0.1
                    );
                }

                tick++;
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }
}￼Enter
