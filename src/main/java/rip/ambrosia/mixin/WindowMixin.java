package rip.ambrosia.mixin;

import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rip.ambrosia.Ambrosia;
import rip.ambrosia.event.WindowInitEvent;

@Mixin(Window.class)
public class WindowMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings,
                      String fullscreenVideoMode, String title, CallbackInfo callbackInfo) {
        Ambrosia.INSTANCE.getEventBus().post(new WindowInitEvent());
    }
}
