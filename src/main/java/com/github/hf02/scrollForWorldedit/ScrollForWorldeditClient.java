package com.github.hf02.scrollForWorldedit;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class ScrollForWorldeditClient implements ClientModInitializer {

	// The KeyBinding declaration and registration are commonly executed here
	// statically

	private static KeyBinding keyBinding;

	@Override
	public void onInitializeClient() {
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.scroll_for_worldedit.spook",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_LEFT_CONTROL,
				"key.scroll_for_worldedit.move"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyBinding.wasPressed()) {
				MinecraftClient.getInstance().player.sendChatMessage("//move 1 -s");
			}
		});
	}

}