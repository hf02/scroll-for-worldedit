package com.github.hf02.scrollForWorldEdit.mixin;

import com.github.hf02.scrollForWorldEdit.client.ScrollForWorldEditClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class TextHudMixin {

	@Inject(at = @At("RETURN"), method = "render()V")
	public void render(
		MatrixStack matrixStack,
		float tickDelta,
		CallbackInfo info
	) {
		ScrollForWorldEditClient.textRenderer.renderText(matrixStack);
	}
}
