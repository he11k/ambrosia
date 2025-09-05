package rip.ambrosia.module.movement

import rip.ambrosia.Ambrosia
import rip.ambrosia.menu.creator.FrameBuilder
import rip.ambrosia.menu.creator.buttons.Checkbox

object NoJumpDelayModule {
    val frame: FrameBuilder = Ambrosia.menu.primary.movement.player.frame("NoJumpDelay", "nojumpdelayModule")
    val enable: Checkbox = frame.createCheckbox("Включить", false, "enable").build()
}