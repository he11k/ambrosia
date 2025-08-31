package rip.ambrosia.event

import net.minecraft.client.render.Frustum
import net.minecraft.client.util.math.MatrixStack
import net.minecraftforge.eventbus.api.Event
import org.joml.Matrix4f

class WindowInitEvent(): Event()
class FramebufferBlitEvent(): Event()
class FrustumUpdateEvent(val frustum: Frustum = Frustum(Matrix4f(),Matrix4f())) : Event()