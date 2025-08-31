package rip.ambrosia.event

import net.minecraft.client.render.Camera
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Vec3d
import net.minecraftforge.eventbus.api.Event
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

class LevelTickEvent(): Event()
class TickEvent(): Event()

class KeyEvent(val action: Int = 0, val key: Int = 0, val scancode: Int = 0) : Event()
class CharacterEvent(val chr: Char = 'a', val modifiers: Int = 0) : Event()

class PlayerVelocityStrafe(val movementInput: Vec3d = Vec3d.ZERO, val speed: Float= 0f, val yaw: Float = 0f, var velocity: Vec3d = Vec3d.ZERO) : Event()
class MovementInputEvent(var forward: Float = 0f, var sideways: Float= 0f, val jump: Boolean = false) : Event()

class SprintEvent(var sprint: Boolean = false, val source: Source = Source.INPUT) : Event() {
    enum class Source {
        INPUT,
        MOVEMENT_TICK,
        NETWORK
    }
}

class RenderWorldEvent(val matrixStack: MatrixStack = MatrixStack(),val camera: Camera = Camera(),val partialTicks: Float = 0f): Event()

