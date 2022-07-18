package com.github.hf02.scrollForWorldEdit.client;

import com.github.hf02.scrollForWorldEdit.ScrollForWorldEdit;
import java.util.Optional;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyManager {

	private Key[] keys;

	public final int count;

	public final KeyBinding modeKey = KeyBindingHelper.registerKeyBinding(
		new KeyBinding(
			"key.scroll-for-worldedit.mode",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_LEFT_CONTROL,
			"key.scroll-for-worldedit.main"
		)
	);
	public final KeyBinding useKey = KeyBindingHelper.registerKeyBinding(
		new KeyBinding(
			"key.scroll-for-worldedit.use",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_LEFT_ALT,
			"key.scroll-for-worldedit.main"
		)
	);
	public final KeyBinding modifierKey = KeyBindingHelper.registerKeyBinding(
		new KeyBinding(
			"key.scroll-for-worldedit.modifier",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_Z,
			"key.scroll-for-worldedit.main"
		)
	);

	private Optional<Key> getKey(String code) {
		for (Key key : keys) {
			if (key.code == code) {
				return Optional.of(key);
			}
		}
		return Optional.empty();
	}

	public boolean isModeKeyActive() {
		return ScrollForWorldEditClient.config.mustHoldUseKeyForModeKey
			? modeKey.isPressed() && useKey.isPressed()
			: modeKey.isPressed();
	}

	private int activeKeyIndex = 0;
	private Key activeKey;

	public void setActiveKey(int index) {
		activeKeyIndex = index % count;
		if (activeKeyIndex < 0) {
			activeKeyIndex = count + activeKeyIndex;
		}
		activeKey = keys[activeKeyIndex];
	}

	public Key getActiveKey() {
		return activeKey;
	}

	public int getActiveKeyIndex() {
		return activeKeyIndex;
	}

	public final ScrollForWorldEditClient scrollClient;

	public KeyManager(ScrollForWorldEditClient scrollForWorldEditClient) {
		this.scrollClient = scrollForWorldEditClient;
		keys =
			new Key[] {
				new Key(
					this,
					"move",
					"/move %s %s -s",
					"scroll-for-worldedit.mode.move",
					"key.scroll-for-worldedit.move",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_UNKNOWN,
					"key.scroll-for-worldedit.main",
					this::runNonNegativeMove
				),
				new Key(
					this,
					"expand",
					"/expand %s %s",
					"scroll-for-worldedit.mode.expand",
					"key.scroll-for-worldedit.expand",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_UNKNOWN,
					"key.scroll-for-worldedit.main",
					this::runMove
				),
				new Key(
					this,
					"contract",
					"/contract %s %s",
					"scroll-for-worldedit.mode.contract",
					"key.scroll-for-worldedit.contract",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_UNKNOWN,
					"key.scroll-for-worldedit.main",
					this::runMove
				),
				new Key(
					this,
					"shift",
					"/shift %s %s",
					"scroll-for-worldedit.mode.shift",
					"key.scroll-for-worldedit.shift",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_UNKNOWN,
					"key.scroll-for-worldedit.main",
					this::runMove
				),
				new Key(
					this,
					"stack",
					"/stack %s %s -s",
					"scroll-for-worldedit.mode.stack",
					"key.scroll-for-worldedit.stack",
					InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_UNKNOWN,
					"key.scroll-for-worldedit.main",
					this::runNonNegativeMove
				),
			};
		count = keys.length;
		setActiveKey(0);
	}

	public boolean isKeyActive(String keyName) {
		Optional<Key> key = getKey(keyName);
		if (!key.isPresent()) {
			ScrollForWorldEdit.LOGGER.warn(
				String.format("isKeyActive(): Unknown key %s", keyName)
			);
			return false;
		}
		if (useKey.isPressed() && activeKey == key.get()) {
			return true;
		}
		return key.get().keybinding.isPressed();
	}

	public void processKeys(TakeScroll scroll) {
		for (Key key : keys) {
			key.run(scroll);
		}
	}

	private void sendCommand(String command) {
		scrollClient.client.player.sendCommand(command);
	}

	// methods to handle Keys

	private void runNonNegativeMove(Key key, TakeScroll scroll) {
		if (scroll.scrollY > 0) {
			sendCommand(
				String.format(
					key.command,
					scroll.scrollY,
					scrollClient.primaryTextDirection()
				)
			);
		} else if (scroll.scrollY < 0) {
			sendCommand(
				String.format(
					key.command,
					-scroll.scrollY,
					scrollClient.primaryTextDirection(true)
				)
			);
		}

		if (scroll.scrollX > 0) {
			sendCommand(
				String.format(
					key.command,
					scroll.scrollX,
					scrollClient.secondaryTextDirection()
				)
			);
		} else if (scroll.scrollX < 0) {
			sendCommand(
				String.format(
					key.command,
					-scroll.scrollX,
					scrollClient.secondaryTextDirection(true)
				)
			);
		}
	}

	private void runMove(Key key, TakeScroll scroll) {
		if (scroll.scrollY != 0) {
			sendCommand(
				String.format(
					key.command,
					scroll.scrollY,
					scrollClient.primaryTextDirection()
				)
			);
		}

		if (scroll.scrollX != 0) {
			sendCommand(
				String.format(
					key.command,
					scroll.scrollX,
					scrollClient.secondaryTextDirection()
				)
			);
		}
	}
}
