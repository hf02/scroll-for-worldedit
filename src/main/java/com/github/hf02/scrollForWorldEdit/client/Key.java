package com.github.hf02.scrollForWorldEdit.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;

public class Key {

	public final String code;
	public final String name;
	public final String command;
	public final KeyBinding keybinding;
	public final KeyManager keyManager;
	private final ScrollForWorldEditClient scrollClient;
	public final KeyHandler handler;

	public Key(
		KeyManager keyManager,
		String code,
		String command,
		String name,
		String keybindingName,
		net.minecraft.client.util.InputUtil.Type type,
		int defaultCode,
		String category,
		KeyHandler handler
	) {
		this.keyManager = keyManager;
		this.code = code;
		this.command = command;
		this.name = name;
		this.scrollClient = keyManager.scrollClient;
		this.handler = handler;
		this.keybinding =
			KeyBindingHelper.registerKeyBinding(
				new KeyBinding(keybindingName, type, defaultCode, category)
			);
	}

	public void run(TakeScroll scroll) {
		boolean isActive = keyManager.isKeyActive(this.code);
		if (isActive) {
			scrollClient.mouseHandler.capturingScroll = true;
			this.handler.callback(this, scroll);
		}
	}
}
