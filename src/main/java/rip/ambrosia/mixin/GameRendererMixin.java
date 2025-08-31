package rip.ambrosia.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import es.boffmedia.mcef.MCEF;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rip.ambrosia.Ambrosia;
import rip.ambrosia.event.RenderWorldEvent;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private Camera camera;

    @Inject(method = "renderWorld", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = Opcodes.GETFIELD, ordinal = 0))
    public void hookWorldRender(RenderTickCounter tickCounter, CallbackInfo ci, @Local(ordinal = 1) Matrix4f matrix4f2) {
        // TODO: Improve this
        var newMatStack = new MatrixStack();

        newMatStack.multiplyPositionMatrix(matrix4f2);
        Ambrosia.INSTANCE.getEventBus().post(new RenderWorldEvent(newMatStack, this.camera, tickCounter.getTickDelta(false)));
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void hookGameRender(CallbackInfo callbackInfo) {
        if (MCEF.isInitialized()) {
            MCEF.getApp().getHandle().N_DoMessageLoopWork();
        }
    }
}
