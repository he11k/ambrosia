package rip.ambrosia.command

import rip.ambrosia.Ambrosia
import rip.ambrosia.command.impl.HelpCommand
import rip.ambrosia.util.language.Language

open class Command(val name: String) {
    open fun run(strings: Array<String>) {

    }

    open fun usage(): String {
        return "${CommandController.prefix}${name}"
    }
}