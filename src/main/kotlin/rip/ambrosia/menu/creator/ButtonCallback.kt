package rip.ambrosia.menu.creator

import rip.ambrosia.menu.creator.buttons.Checkbox

interface ButtonCallback {
    fun run(checkbox: Checkbox)
}