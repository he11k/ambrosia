package rip.ambrosia.command.impl

import rip.ambrosia.command.Command
import rip.ambrosia.command.MessageHandler
import rip.ambrosia.util.language.Language

object HelpCommand : Command("help") {
    override fun run(strings: Array<String>) {
        val errorLang = Language.string()
            .add(Language.EN, "Not implemented")
            .add(Language.RU, "Недоступно")
        MessageHandler.send(errorLang)
    }
}