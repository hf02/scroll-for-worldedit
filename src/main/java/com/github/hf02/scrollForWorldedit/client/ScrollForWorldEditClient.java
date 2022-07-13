package com.github.hf02.scrollForWorldEdit.client;

import com.github.hf02.scrollForWorldEdit.ScrollForWorldEdit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ScrollForWorldEditClient implements ClientModInitializer {

	private static KeyBinding moveKey;
	private MouseScrollHandler mouseHandler = new MouseScrollHandler();

	@Override
	public void onInitializeClient() {
		ScrollForWorldEdit.LOGGER.info("active...");
		moveKey =
			KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
					"key.scroll_for_worldedit.move",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_LEFT_CONTROL,
					"key.scroll_for_worldedit.main"
				)
			);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (moveKey.isPressed()) {
				client.player.sendCommand("/move 1 -s");
				client.player.sendCommand(Double.toString(client.mouse.getX()));
			}
		});
	}
}
