package com.github.hf02.scrollForWorldEdit.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextRenderer {

	public void renderText(MatrixStack matrixStack) {
		int color = changingMode
			? ScrollForWorldEditClient.config.modeColor
			: ScrollForWorldEditClient.config.useColor;
		if (shouldRenderText == true) {
			for (int i = 0; i < text.length; i++) {
				Text line = text[i];
				if (changingMode || i == text.length - 1) {
					drawText(
						matrixStack,
						line,
						getTextPosX(line),
						getTextPosY(line, -(text.length - i)),
						color,
						true
					);
				}
			}
		}
	}

	public float getTextPosX(Text text) {
		MinecraftClient client = MinecraftClient.getInstance();
		Window window = client.getWindow();
		return (
			(window.getScaledWidth() * 0.5f) -
			(client.textRenderer.getWidth(text) * 0.5f)
		);
	}

	public float getTextPosY(Text text, float offset) {
		MinecraftClient client = MinecraftClient.getInstance();
		Window window = client.getWindow();
		return (
			(window.getScaledHeight() * 0.5f) +
			(-2 * (float) window.getScaleFactor()) +
			(client.textRenderer.fontHeight * offset)
		);
	}

	public void drawText(
		MatrixStack matrixStack,
		Text text,
		float textPosX,
		float textPosY,
		int color,
		boolean shadow
	) {
		MinecraftClient client = MinecraftClient.getInstance();
		matrixStack.push();
		matrixStack.translate(textPosX, textPosY, 0);
		matrixStack.scale(1, 1, 1);
		matrixStack.translate(-textPosX, -textPosY, 0);
		

		client.textRenderer.draw(
			text,
			textPosX,
			textPosY,
			color,
			shadow,
			matrixStack
		);
	}

	public boolean shouldRenderText = true;
	public boolean changingMode = false;

	public Text[] text = { Text.literal("...") };
}
