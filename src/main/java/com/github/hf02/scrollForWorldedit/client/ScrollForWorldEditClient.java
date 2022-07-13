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
	private static KeyBinding contractKey;
	private static KeyBinding expandKey;
	private static KeyBinding shiftKey;
	private MouseScrollHandler mouseHandler = new MouseScrollHandler();

	@Override
	public void onInitializeClient() {
		ScrollForWorldEdit.LOGGER.info("active...");
		moveKey =
			KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
					"key.scroll_for_worldedit.move",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_UNKNOWN,
					"key.scroll_for_worldedit.main"
				)
			);
		contractKey =
			KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
					"key.scroll_for_worldedit.contract",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_UNKNOWN,
					"key.scroll_for_worldedit.main"
				)
			);
		expandKey =
			KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
					"key.scroll_for_worldedit.expand",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_UNKNOWN,
					"key.scroll_for_worldedit.main"
				)
			);
		shiftKey =
			KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
					"key.scroll_for_worldedit.shift",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_UNKNOWN,
					"key.scroll_for_worldedit.main"
				)
			);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			mouseHandler.capturingScroll = false;
			final int scrollTaken = mouseHandler.takeScroll();
			if (moveKey.isPressed()) {
				mouseHandler.capturingScroll = true;
				if (scrollTaken > 0) {
					client.player.sendCommand(
						String.format("/move %s -s", scrollTaken)
					);
				} else if (scrollTaken < 0) {
					client.player.sendCommand(
						String.format("/move %s back -s", -scrollTaken)
					);
				}
			}
		});
	}
}
