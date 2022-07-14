package com.github.hf02.scrollForWorldEdit.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextRenderer {

	public void renderText(MatrixStack matrixStack) {
		if (shouldRenderText == true) {
			MinecraftClient client = MinecraftClient.getInstance();
			Window window = client.getWindow();
			float textPosX =
				(window.getScaledWidth() * 0.5f) -
				(client.textRenderer.getWidth(text) * 0.5f);
			float textPosY =
				(window.getScaledHeight() * 0.5f) +
				(-2 * (float) window.getScaleFactor()) -
				(client.textRenderer.fontHeight);

			matrixStack.push();
			matrixStack.translate(textPosX, textPosY, 0);
			matrixStack.scale(1, 1, 1);
			matrixStack.translate(-textPosX, -textPosY, 0);

			client.textRenderer.drawWithShadow(
				matrixStack,
				text,
				textPosX,
				textPosY,
				0xffffff
			);
		}
	}

	public boolean shouldRenderText = true;
	public Text text = Text.literal("...");
}
