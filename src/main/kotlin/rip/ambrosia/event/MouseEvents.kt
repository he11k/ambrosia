package rip.ambrosia.event

import net.minecraftforge.eventbus.api.Cancelable
import net.minecraftforge.eventbus.api.Event
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Cancelable
class MouseClickEvent(val action: Int = 0, val button: Int= 0): Event()
class MouseScrollEvent(val h: Float = 0f,val v: Float = 0f) : Event()