package rip.ambrosia.module

import rip.ambrosia.Ambrosia
import rip.ambrosia.menu.creator.FrameBuilder
import rip.ambrosia.menu.creator.buttons.Checkbox
import rip.ambrosia.menu.creator.condition.CheckboxCondition
import rip.ambrosia.menu.creator.condition.Condition

class Test2 {
    val autojumpFrame: FrameBuilder = Ambrosia.menu.primary.main.action.frame("Авто-Прыжок", "autojump")
    val activateEnable: Checkbox = autojumpFrame.createCheckbox("Активировать включение", true, "activateenable").build()
    val enable: Checkbox = autojumpFrame.createCheckbox("Включить", false, "enable").activeCondition(CheckboxCondition(activateEnable))!!.build()
}