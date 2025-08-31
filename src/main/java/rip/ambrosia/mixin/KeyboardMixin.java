package rip.ambrosia.mixin;


import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rip.ambrosia.Ambrosia;
import rip.ambrosia.event.CharacterEvent;
import rip.ambrosia.event.KeyEvent;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long window, int key, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
        if (window != MinecraftClient.getInstance().getWindow().getHandle()) {
            return;
        }

        Ambrosia.INSTANCE.getEventBus().post(new KeyEvent(action, key, scanCode));

    }

    @Inject(method = "onChar", at = @At("HEAD"))
    private void onChar(long window, int codePoint, int modifiers, CallbackInfo ci) {
        if (window != MinecraftClient.getInstance().getWindow().getHandle()) {
            return;
        }

        Ambrosia.INSTANCE.getEventBus().post(new CharacterEvent((char) codePoint, modifiers));

    }
}
