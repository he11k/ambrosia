package rip.ambrosia.command.impl

import net.minecraft.text.Text
import rip.ambrosia.command.Command
import rip.ambrosia.command.CommandController.prefix
import rip.ambrosia.command.MessageHandler
import rip.ambrosia.util.language.Language

object LanguageCommand : Command("lang") {
    override fun run(strings: Array<String>) {
        if (strings.size < 2) {
            return
        }
        if (Language.containsLanguage(strings[1])) {
            Language.currentLanguage = Language.fromTitle(strings[1])
            val message = Language.text()
                .add(
                    Language.EN,
                    Text.empty().append("Enabled ").append(Text.empty().append("english ").withColor(0xFF5555))
                        .append("language")
                )
                .add(
                    Language.RU,
                    Text.empty().append("Включен ").append(Text.empty().append("русский ").withColor(0xFF5555))
                        .append("язык")
                )
            MessageHandler.send(message.get())
        } else if (strings[1].contains("list")) {
            val list = Language.string()
                .add(Language.EN, "List of languages: ")
                .add(Language.RU, "Список языков: ")
            MessageHandler.send(list.get())
            for (entry in Language.entries) {
                MessageHandler.send(entry.name + " - " + entry.title)
            }
        }
    }
}