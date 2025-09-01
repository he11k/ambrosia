package rip.ambrosia.module

import rip.ambrosia.Ambrosia
import rip.ambrosia.menu.creator.FrameBuilder
import rip.ambrosia.menu.creator.buttons.Checkbox

class Test {
    val frame: FrameBuilder = Ambrosia.menu.primary.main.attackAura.frame("Основное")
    val enable: Checkbox = frame.createCheckbox("Включить", true).build()
    fun lol () {

    }
}