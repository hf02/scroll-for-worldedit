package com.github.hf02.scrollForWorldEdit.client;

import com.github.hf02.scrollForWorldEdit.ScrollForWorldEdit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ScrollForWorldEditClient implements ClientModInitializer {

	public final KeyManager keyHandler = new KeyManager(this);

	public static TextRenderer textRenderer = new TextRenderer();
	public MinecraftClient client;
	public final MouseScrollHandler mouseHandler = new MouseScrollHandler();

	@Override
	public void onInitializeClient() {
		ScrollForWorldEdit.LOGGER.info("active...");
		client = MinecraftClient.getInstance();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) return;

			mouseHandler.capturingScroll =
				keyHandler.modeKey.isPressed() || keyHandler.useKey.isPressed();
			textRenderer.shouldRenderText = mouseHandler.capturingScroll;

			final int scrollTakenY = mouseHandler.takeScrollY();
			final int scrollTakenX = mouseHandler.takeScrollX();

			if (keyHandler.modeKey.isPressed()) {
				keyHandler.setActiveKey(
					keyHandler.getActiveKeyIndex() - scrollTakenY
				);
			} else {
				if (keyHandler.isKeyActive("move")) {
					mouseHandler.capturingScroll = true;
					if (scrollTakenY > 0) {
						client.player.sendCommand(
							String.format(
								"/move %s %s -s",
								scrollTakenY,
								getTextDirection()
							)
						);
					} else if (scrollTakenY < 0) {
						client.player.sendCommand(
							String.format(
								"/move %s %s -s",
								-scrollTakenY,
								getTextDirection(true)
							)
						);
					}
					if (scrollTakenX > 0) {
						client.player.sendCommand(
							String.format(
								"/move %s %s -s",
								scrollTakenX,
								getTextDirection(-90, 0)
							)
						);
					} else if (scrollTakenX < 0) {
						client.player.sendCommand(
							String.format(
								"/move %s %s -s",
								-scrollTakenX,
								getTextDirection(90, 0)
							)
						);
					}
				}
				if (keyHandler.isKeyActive("expand")) {
					mouseHandler.capturingScroll = true;
					if (scrollTakenY != 0) {
						client.player.sendCommand(
							String.format(
								"/expand %s %s",
								scrollTakenY,
								getTextDirection()
							)
						);
					}
					if (scrollTakenX != 0) {
						client.player.sendCommand(
							String.format(
								"/expand %s %s",
								scrollTakenX,
								getTextDirection(-90, 0)
							)
						);
					}
				}
				if (keyHandler.isKeyActive("contract")) {
					mouseHandler.capturingScroll = true;
					if (scrollTakenY != 0) {
						client.player.sendCommand(
							String.format(
								"/contract %s %s",
								scrollTakenY,
								getTextDirection()
							)
						);
					}
					if (scrollTakenX != 0) {
						client.player.sendCommand(
							String.format(
								"/contract %s %s",
								scrollTakenX,
								getTextDirection(-90, 0)
							)
						);
					}
				}
				if (keyHandler.isKeyActive("shift")) {
					mouseHandler.capturingScroll = true;
					if (scrollTakenY != 0) {
						client.player.sendCommand(
							String.format(
								"/shift %s %s",
								scrollTakenY,
								getTextDirection()
							)
						);
					}
					if (scrollTakenX != 0) {
						client.player.sendCommand(
							String.format(
								"/shift %s %s",
								scrollTakenX,
								getTextDirection(-90, 0)
							)
						);
					}
				}
			}

			textRenderer.text =
				Text.translatable(
					"scroll_for_worldedit.hud_selector",
					Text.translatable(keyHandler.getActiveKey().name),
					keyHandler.getActiveKeyIndex() + 1,
					keyHandler.count
				);
		});
	}

	public String getTextDirection(float rotationX, float rotationY) {
		if (inRange(rotationX, 60, 90)) {
			return "down";
		}
		if (inRange(rotationX, -90, -60)) {
			return "up";
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
