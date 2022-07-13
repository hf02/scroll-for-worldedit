package com.github.hf02.scrollForWorldEdit.client;

import com.github.hf02.scrollForWorldEdit.ScrollForWorldEdit;
import net.minecraft.util.ActionResult;

public class MouseScrollHandler {

	private double scrollLeft = 0;

	public boolean capturingScroll = false;

	public double scrollThreshold = 1;

	public int takeScroll() {
		int scrolled = 0;
		while (true) {
			final double scrollLeftRounded =
				scrollThreshold * Math.floor(scrollLeft / scrollThreshold);
			if (scrollLeft == 0) {
				break;
			} else if (scrollLeftRounded > 0) {
				scrollLeft -= scrollThreshold;
				scrolled++;
			} else {
				scrollLeft += scrollThreshold;
				scrolled--;
			}
			if (scrolled > 1000) {
				ScrollForWorldEdit.LOGGER.warn("likely infinite loop");
			}
		}
		return scrolled;
	}

	public void updateScrollCapture() {
		if (capturingScroll == false) {
			scrollLeft = 0;
		}
	}

	public MouseScrollHandler() {
		MouseScrollCallback.EVENT.register((horizontal, vertical) -> {
			if (capturingScroll) {
				scrollLeft += vertical;
				return ActionResult.SUCCESS;
			}
			return ActionResult.PASS;
		});
	}
}
