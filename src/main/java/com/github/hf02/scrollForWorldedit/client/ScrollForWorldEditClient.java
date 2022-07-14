package com.github.hf02.scrollForWorldEdit.client;

import com.github.hf02.scrollForWorldEdit.config.ScrollForWorldEditConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ScrollForWorldEditClient implements ClientModInitializer {

	public KeyManager keyManager;

	public static TextRenderer textRenderer = new TextRenderer();
	public MinecraftClient client;
	public final MouseScrollHandler mouseHandler = new MouseScrollHandler();
	public static ScrollForWorldEditConfig config;

	@Override
	public void onInitializeClient() {
		this.keyManager = new KeyManager(this);

		AutoConfig.register(
			ScrollForWorldEditConfig.class,
			JanksonConfigSerializer::new
		);
		config =
			AutoConfig
				.getConfigHolder(ScrollForWorldEditConfig.class)
				.getConfig();

		client = MinecraftClient.getInstance();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) return;

			mouseHandler.capturingScroll =
				keyManager.modeKey.isPressed() || keyManager.useKey.isPressed();
			textRenderer.shouldRenderText = mouseHandler.capturingScroll;

			final TakeScroll scrollTaken = mouseHandler.takeScroll();

			if (keyManager.modeKey.isPressed()) {
				keyManager.setActiveKey(
					(int) (keyManager.getActiveKeyIndex() - scrollTaken.scrollY)
				);
			} else {
				keyManager.processKeys(scrollTaken);
			}

			textRenderer.text =
				Text.translatable(
					"scroll_for_worldedit.hud_selector",
					Text.translatable(keyManager.getActiveKey().name),
					keyManager.getActiveKeyIndex() + 1,
					keyManager.count
				);
		});
	}

	public String getTextDirection(
		float rotationX,
		float rotationY,
		boolean forceVerticalLook
	) {
		if (config.verticalLook || forceVerticalLook) {
			if (inRange(rotationX, 60, 90)) {
				return "down";
			}
			if (inRange(rotationX, -90, -60)) {
				return "up";
			}
		}
		if (inRange(rotationY, 0, 45) || inRange(rotationY, 315, 360)) {
			return "south";
		}
		if (inRange(rotationY, 45, 135)) {
			return "west";
		}
		if (inRange(rotationY, 135, 225)) {
			return "north";
		}
		if (inRange(rotationY, 225, 315)) {
			return "east";
		}
		return "me";
	}

	public String getTextDirection(float rotationX, float rotationY) {
		return getTextDirection(rotationX, rotationY, false);
	}

	public String getTextDirection(boolean reverse) {
		if (reverse) {
			float rotationX = -(client.player.getRotationClient().x) % 180;
			float rotationY = (client.player.getRotationClient().y + 180) % 360;
			if (rotationY < 0) rotationY += 360;
			return getTextDirection(rotationX, rotationY);
		} else {
			float rotationX = (client.player.getRotationClient().x) % 180;
			float rotationY = (client.player.getRotationClient().y) % 360;
			if (rotationY < 0) rotationY += 360;
			return getTextDirection(rotationX, rotationY);
		}
	}

	public String getTextDirection() {
		return getTextDirection(false);
	}

	private boolean inRange(double number, double min, double max) {
		return min <= number && number <= max;
	}
}
