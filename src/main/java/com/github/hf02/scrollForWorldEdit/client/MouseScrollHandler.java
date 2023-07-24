package com.github.hf02.scrollForWorldEdit.client;

import net.minecraft.util.ActionResult;

public class MouseScrollHandler {

	private double scrollLeftY = 0;

	private double scrollLeftX = 0;

	public boolean capturingScroll = false;

	public int takeScrollY() {
		int scrolled = 0;
		if (ScrollForWorldEditClient.config.scrollThresholdY <= 0) {
			ScrollForWorldEditClient.config.scrollThresholdY = 1;
		}
		while (true) {
			final double scrollLeftRounded =
				ScrollForWorldEditClient.config.scrollThresholdY *
				Math.floor(
					scrollLeftY /
					ScrollForWorldEditClient.config.scrollThresholdY
				);
			if (scrollLeftRounded == 0) {
				break;
			} else if (scrollLeftRounded > 0) {
				scrollLeftY -= ScrollForWorldEditClient.config.scrollThresholdY;
				scrolled++;
			} else {
				scrollLeftY += ScrollForWorldEditClient.config.scrollThresholdY;
				scrolled--;
			}
		}
		return scrolled;
	}

	public int takeScrollX() {
		int scrolled = 0;
		if (ScrollForWorldEditClient.config.scrollThresholdX <= 0) {
			ScrollForWorldEditClient.config.scrollThresholdX = 1;
		}
		while (true) {
			final double scrollLeftRounded =
				ScrollForWorldEditClient.config.scrollThresholdY *
				Math.floor(
					scrollLeftX /
					ScrollForWorldEditClient.config.scrollThresholdX
				);
			if (scrollLeftRounded == 0) {
				break;
			} else if (scrollLeftRounded > 0) {
				scrollLeftX -= ScrollForWorldEditClient.config.scrollThresholdX;
				scrolled++;
			} else {
				scrollLeftX += ScrollForWorldEditClient.config.scrollThresholdX;
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
