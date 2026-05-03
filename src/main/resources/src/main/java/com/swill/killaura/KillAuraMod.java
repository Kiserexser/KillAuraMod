package com.swill.killaura;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.swill.killaura.modules.KillAura;

public class KillAuraMod implements ClientModInitializer {
    public static KillAura killAura;
    public static KeyBinding guiKey;

    @Override
    public void onInitializeClient() {
        killAura = new KillAura();
        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.swill.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "SWILL"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (killAura != null) killAura.tick();
        });
        System.out.println("[SWILL] KillAura загружен");
    }
}
