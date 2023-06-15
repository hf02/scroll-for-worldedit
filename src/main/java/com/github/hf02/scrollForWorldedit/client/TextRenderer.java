package com.github.hf02.scrollForWorldEdit.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;

public class TextRenderer {

	public void renderText(DrawContext context) {
		int color = changingMode
			? ScrollForWorldEditClient.config.modeColor
			: ScrollForWorldEditClient.config.useColor;
		if (shouldRenderText == true) {
			for (int i = 0; i < text.length; i++) {
				Text line = text[i];
				if (changingMode || i == text.length - 1) {
					drawText(
						context,
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

	public int getTextPosX(Text text) {
		MinecraftClient client = MinecraftClient.getInstance();
		Window window = client.getWindow();
		return (int) (
			(window.getScaledWidth() * 0.5f) -
			(client.textRenderer.getWidth(text) * 0.5f)
		);
	}

	public int getTextPosY(Text text, float offset) {
		MinecraftClient client = MinecraftClient.getInstance();
		Window window = client.getWindow();
		return (int) (
			(window.getScaledHeight() * 0.5f) +
			(-2 * (float) window.getScaleFactor()) +
			(client.textRenderer.fontHeight * offset)
		);
	}

	public void drawText(
		DrawContext context,
		Text text,
		int textPosX,
		int textPosY,
		int color,
		boolean shadow
	) {
		MinecraftClient client = MinecraftClient.getInstance();
		context.drawText(
			client.textRenderer,
			text,
			textPosX,
			textPosY,
			color,
			shadow
		);
	}

	public boolean shouldRenderText = true;
	public boolean changingMode = false;

	public Text[] text = { Text.literal("...") };
}
