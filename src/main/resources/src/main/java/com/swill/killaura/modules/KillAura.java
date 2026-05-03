package com.swill.killaura.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import java.util.concurrent.ThreadLocalRandom;

public class KillAura {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private boolean enabled = true;
    private int delay = 0;

    public void tick() {
        if (!enabled || mc.player == null || mc.world == null) return;
        Entity target = getTarget(4.8);
        if (target == null) return;
        if (delay <= 0) {
            mc.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(target, false));
            mc.player.swingHand(Hand.MAIN_HAND);
            delay = ThreadLocalRandom.current().nextInt(18, 27);
        } else delay--;
    }

    private Entity getTarget(double range) {
        Entity best = null;
        double bestDist = range + 1;
        for (Entity e : mc.world.getEntities()) {
            if (e == mc.player) continue;
            if (!(e instanceof LivingEntity) || !e.isAlive()) continue;
            double dist = mc.player.distanceTo(e);
            if (dist < bestDist && dist <= range) {
                bestDist = dist;
                best = e;
            }
        }
        return best;
    }

    public void toggle() { enabled = !enabled; }
    public boolean isEnabled() { return enabled; }
}
