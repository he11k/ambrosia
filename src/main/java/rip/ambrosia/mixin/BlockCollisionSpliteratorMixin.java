package rip.ambrosia.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockCollisionSpliterator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import rip.ambrosia.Ambrosia;
import rip.ambrosia.event.BlockShapeEvent;
import rip.ambrosia.event.PlayerJumpEvent;

@Mixin(BlockCollisionSpliterator.class)
public class BlockCollisionSpliteratorMixin {
    @Shadow
    @Final
    private BlockPos.Mutable pos;

    /**
     * Hook collision shape event
     *
     * @param original voxel shape
     * @return possibly modified voxel shape
     */
    @ModifyExpressionValue(method = "computeNext", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/ShapeContext;getCollisionShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/CollisionView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/shape/VoxelShape;"
    ))
    private VoxelShape hookCollisionShape(VoxelShape original, @Local BlockState blockState) {
        if (this.pos == null) {
            return original;
        }
        BlockShapeEvent shapeEvent;
        Ambrosia.INSTANCE.getEventBus().post(shapeEvent = new BlockShapeEvent(blockState, this.pos, original));
        return shapeEvent.getShape();
    }
}
