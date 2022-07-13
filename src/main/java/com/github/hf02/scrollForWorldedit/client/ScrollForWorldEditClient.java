package com.github.hf02.scrollForWorldEdit.client;

import org.lwjgl.glfw.GLFW;

import com.github.hf02.scrollForWorldEdit.ScrollForWorldEdit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class ScrollForWorldEditClient implements ClientModInitializer {

	// The KeyBinding declaration and registration are commonly executed here
	// statically

	private static KeyBinding keyBinding;

	@Override
	public void onInitializeClient() {
		ScrollForWorldEdit.LOGGER.info("active...");
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.scroll_for_worldedit.move",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_LEFT_CONTROL,
				"key.scroll_for_worldedit.main"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (keyBinding.isPressed()) {
				client.player.sendCommand("/move 1 -s");
			}
		});
	}

}