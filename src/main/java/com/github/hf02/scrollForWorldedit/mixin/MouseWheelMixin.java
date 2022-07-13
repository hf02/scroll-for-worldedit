package com.github.hf02.scrollForWorldEdit.mixin;

import com.github.hf02.scrollForWorldEdit.ScrollForWorldEdit;
import com.github.hf02.scrollForWorldEdit.client.MouseScrollCallback;
import net.minecraft.client.Mouse;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseWheelMixin {

	@Inject(at = @At("HEAD"), method = "onMouseScroll()V", cancellable = true)
	private void onMouseScroll(
		long window,
		double horizontal,
		double vertical,
		CallbackInfo info
	) {
		ActionResult result = MouseScrollCallback.EVENT
			.invoker()
			.interact(horizontal, vertical);

		if (result == ActionResult.SUCCESS) {
			info.cancel();
		}
	}
}
