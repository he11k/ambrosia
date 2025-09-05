package rip.ambrosia.menu.creator.condition

import rip.ambrosia.menu.creator.buttons.MultiSelectbox

class MultiSelectboxCondition(button: MultiSelectbox, val selected: String) : Condition<String, MultiSelectbox>(button) {
    override fun getValue(): String {
        return selected
    }

}