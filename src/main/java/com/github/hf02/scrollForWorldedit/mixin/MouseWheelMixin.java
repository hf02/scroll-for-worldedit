package com.github.hf02.scrollForWorldEdit.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.hf02.scrollForWorldEdit.ScrollForWorldEdit;

@Mixin(Mouse.class)
public class MouseWheelMixin {
	@Inject(at = @At("HEAD"), method = "getX()V", cancellable = true)
	private void getX(CallbackInfoReturnable<Integer> info) {
		info.setReturnValue(0);
		info.cancel();
		ScrollForWorldEdit.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
