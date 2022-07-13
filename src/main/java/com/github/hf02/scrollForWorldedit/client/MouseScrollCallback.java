package com.github.hf02.scrollForWorldEdit.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

/**
 * callback for the mouse scrolling.
 * returning SUCCESS will cancel minecraft's handling of the mouse
 * wheel.
 */
public interface MouseScrollCallback {
	Event<MouseScrollCallback> EVENT = EventFactory.createArrayBacked(
		MouseScrollCallback.class,
		listeners ->
			(horizontal, vertical) -> {
				for (MouseScrollCallback listener : listeners) {
					ActionResult result = listener.interact(
						horizontal,
						vertical
					);

					if (result != ActionResult.PASS) {
						return result;
					}
				}
				return ActionResult.PASS;
			}
	);

	ActionResult interact(double horizontal, double vertical);
}
