package com.github.hf02.scrollForWorldEdit.client;

import com.github.hf02.scrollForWorldEdit.ScrollForWorldEdit;
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
		boolean allowVerticalLook
	) {
		if (allowVerticalLook) {
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
		return getTextDirection(rotationX, rotationY, true);
	}

	public String getTextDirection() {
		return getTextDirection(getRotX(false), getRotY(false));
	}

	public String getTextDirection(
		TextDirectionType directionType,
		boolean reverse
	) {
		float rotationX = getRotX(reverse);
		float rotationY = getRotY(reverse);
		switch (directionType) {
			case Normal:
				return getTextDirection(rotationX, rotationY);
			case Horizontal:
				return getTextDirection(rotationX, rotationY, false);
			case Sideways:
				return getTextDirection(
					rotationX,
					getRotY(rotationY + 90, false)
				);
			case SidewaysHorizontal:
				return getTextDirection(
					rotationX,
					getRotY(rotationY + 90, false),
					false
				);
			case Vertical:
				return getTextDirection(reverse ? 90 : -90, 0);
			default:
				return getTextDirection(rotationX, rotationY);
		}
	}

	public String getTextDirection(TextDirectionType directionType) {
		return getTextDirection(directionType, false);
	}

	public String primaryTextDirection(boolean reverse) {
		TextDirectionType directionType = config.primaryDirection;
		if (this.keyManager.modifierKey.isPressed()) {
			directionType = config.primaryDirectionModifier;
		}
		return getTextDirection(directionType, reverse);
	}

	public String primaryTextDirection() {
		return primaryTextDirection(false);
	}

	public String secondaryTextDirection(boolean reverse) {
		TextDirectionType directionType = config.secondaryDirection;
		if (this.keyManager.modifierKey.isPressed()) {
			directionType = config.secondaryDirectionModifier;
		}
		return getTextDirection(directionType, reverse);
	}

	public String secondaryTextDirection() {
		return secondaryTextDirection(false);
	}

	public float getRotX(float rotation, boolean reverse) {
		if (reverse) {
			return -(rotation) % 180;
		} else {
			return (rotation) % 180;
		}
	}

	public float getRotX(boolean reverse) {
		return getRotY(client.player.getRotationClient().x, reverse);
	}

	public float getRotY(float rotation, boolean reverse) {
		float rotationY;
		if (reverse) {
			rotationY = (rotation + 180) % 360;
		} else {
			rotationY = rotation % 360;
		}
		if (rotationY < 0) rotationY += 360;
		return rotationY;
	}

	public float getRotY(boolean reverse) {
		return getRotY(client.player.getRotationClient().y, reverse);
	}

	private boolean inRange(double number, double min, double max) {
		return min <= number && number <= max;
	}
}
