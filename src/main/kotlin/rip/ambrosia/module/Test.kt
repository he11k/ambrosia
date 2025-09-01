package rip.ambrosia.module

import rip.ambrosia.Ambrosia
import rip.ambrosia.menu.creator.FrameBuilder
import rip.ambrosia.menu.creator.buttons.Checkbox

class Test {

    val mainFrame: FrameBuilder = Ambrosia.menu.primary.main.attackAura.frame("Основное", "main")
    val enable: Checkbox = mainFrame.createCheckbox("Включить", true, "enable").build()
    val condFrame: FrameBuilder = Ambrosia.menu.primary.main.attackAura.frame("Условия", "condition")
    val visualFrame: FrameBuilder = Ambrosia.menu.primary.main.attackAura.frame("Визуализация", "visual")

    val legitMain: FrameBuilder = Ambrosia.menu.primary.main.legit.frame("Основное1", "main")
    val enableL: Checkbox = legitMain.createCheckbox("Включить", true, "enable").build()
    val condLegit: FrameBuilder = Ambrosia.menu.primary.main.legit.frame("Условия1", "condition")
    val visualLegit: FrameBuilder = Ambrosia.menu.primary.main.legit.frame("Визуализация1", "visual")
}