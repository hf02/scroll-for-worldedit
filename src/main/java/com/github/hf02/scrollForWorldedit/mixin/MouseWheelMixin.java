package com.github.hf02.scrollForWorldEdit.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.hf02.scrollForWorldEdit.ScrollForWorldEdit;

@Mixin(Mouse.class)
public class MouseWheelMixin {
	@Inject(at = @At("HEAD"), method = "onMouseScroll()V", cancellable = true)
	private void onMouseScroll(CallbackInfo info) {
		ScrollForWorldEdit.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
