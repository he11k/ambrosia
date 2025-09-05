package rip.ambrosia.menu.creator.condition

import rip.ambrosia.menu.creator.buttons.Checkbox

class CheckboxCondition(button: Checkbox) : Condition<Boolean, Checkbox>(button) {
    override fun getValue(): Boolean {
        return button.value
    }
}