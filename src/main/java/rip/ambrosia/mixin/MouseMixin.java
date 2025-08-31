package rip.ambrosia.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rip.ambrosia.Ambrosia;
import rip.ambrosia.event.MouseClickEvent;
import rip.ambrosia.event.MouseScrollEvent;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onMouseButton", cancellable = true, at = @At("HEAD"))
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo callbackInfo) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (window == mc.getWindow().getHandle()) {
            MouseClickEvent mouseClickEvent;
            Ambrosia.INSTANCE.getEventBus().post(mouseClickEvent = new MouseClickEvent(action, button));
            if (callbackInfo.isCancellable() && mouseClickEvent.isCanceled()) {
                callbackInfo.cancel();
            }
        }
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"))
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo callbackInfo) {
        if (window == MinecraftClient.getInstance().getWindow().getHandle()) {
            boolean bl = MinecraftClient.getInstance().options.getDiscreteMouseScroll().getValue();
            double d = MinecraftClient.getInstance().options.getMouseWheelSensitivity().getValue();
            double e = (bl ? Math.signum(horizontal) : horizontal) * d;
            double f = (bl ? Math.signum(vertical) : vertical) * d;
            Ambrosia.INSTANCE.getEventBus().post(new MouseScrollEvent((float) e, (float) f));
        }
    }
}