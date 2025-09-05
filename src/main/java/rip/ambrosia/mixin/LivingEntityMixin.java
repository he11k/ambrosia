package rip.ambrosia.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rip.ambrosia.Ambrosia;
import rip.ambrosia.event.KeyEvent;
import rip.ambrosia.event.PlayerJumpEvent;
import rip.ambrosia.module.movement.AirJumpModule;
import rip.ambrosia.module.movement.NoJumpDelayModule;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {
    @Shadow
    public int jumpingCooldown;
    @Shadow
    public boolean jumping;
    @Shadow
    public abstract void jump();
    @Shadow
    public abstract float getJumpVelocity();
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void hookTickMovement(CallbackInfo callbackInfo) {
        var noJumpDelay = NoJumpDelayModule.INSTANCE.getEnable().getValue()  && !AirJumpModule.INSTANCE.getAllowJump();

        if (noJumpDelay) {
            jumpingCooldown = 0;
        }
    }

    @Inject(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;jumping:Z"))
    private void hookAirJump(CallbackInfo callbackInfo) {
        if (AirJumpModule.INSTANCE.getAllowJump() && jumping && jumpingCooldown == 0) {
            this.jump();
            jumpingCooldown = 10;
        }
    }
    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void hookJumpEvent(CallbackInfo ci) {
        if ((Object) this != MinecraftClient.getInstance().player) {
            return;
        }
        PlayerJumpEvent jumpEvent;
        Ambrosia.INSTANCE.getEventBus().post(jumpEvent = new PlayerJumpEvent(getJumpVelocity(), this.getYaw()));

        if (jumpEvent.isCancelable() && jumpEvent.isCanceled()) {
            ci.cancel();
        }
    }

}
