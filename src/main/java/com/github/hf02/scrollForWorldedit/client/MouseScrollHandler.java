package com.github.hf02.scrollForWorldEdit.client;

import com.github.hf02.scrollForWorldEdit.ScrollForWorldEdit;
import net.minecraft.util.ActionResult;

public class MouseScrollHandler {

	private double scrollLeftY = 0;

	private double scrollLeftX = 0;

	public boolean capturingScroll = false;

	public double scrollThresholdY = 1;
	public double scrollThresholdX = 1;

	public int takeScrollY() {
		int scrolled = 0;
		while (true) {
			final double scrollLeftRounded =
				scrollThresholdY * Math.floor(scrollLeftY / scrollThresholdY);
			if (scrollLeftY == 0) {
				break;
			} else if (scrollLeftRounded > 0) {
				scrollLeftY -= scrollThresholdY;
				scrolled++;
			} else {
				scrollLeftY += scrollThresholdY;
				scrolled--;
			}
		}
		return scrolled;
	}

	public int takeScrollX() {
		int scrolled = 0;
		while (true) {
			final double scrollLeftRounded =
				scrollThresholdY * Math.floor(scrollLeftX / scrollThresholdX);
			if (scrollLeftX == 0) {
				break;
			} else if (scrollLeftRounded > 0) {
				scrollLeftX -= scrollThresholdX;
				scrolled++;
			} else {
				scrollLeftX += scrollThresholdX;
				scrolled--;
			}
		}
		return scrolled;
	}

	public TakeScroll takeScroll() {
		return new TakeScroll(takeScrollX(), takeScrollY());
	}

	public void updateScrollCapture() {
		if (capturingScroll == false) {
			scrollLeftY = 0;
		}
	}

	public MouseScrollHandler() {
		MouseScrollCallback.EVENT.register((horizontal, vertical) -> {
			if (capturingScroll) {
				scrollLeftY += vertical;
				scrollLeftX += horizontal;
				return ActionResult.SUCCESS;
			}
			return ActionResult.PASS;
		});
	}
}
