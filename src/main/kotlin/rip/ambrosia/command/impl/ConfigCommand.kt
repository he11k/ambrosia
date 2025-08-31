package rip.ambrosia.command.impl

import net.minecraft.text.Text
import rip.ambrosia.command.Command
import rip.ambrosia.command.MessageHandler
import rip.ambrosia.config.Config
import rip.ambrosia.util.language.Language

object ConfigCommand : Command("config") {

    val config = Config()

    override fun run(strings: Array<String>) {
        if (strings.size < 3) {
            return
        }
        val cfg = strings[2] + ".amb";
        if (strings[1].contains("load")) {
            var message = Language.text()
                .add(
                    Language.EN,
                    Text.empty().append("Config ").append(Text.empty().append("$cfg ").withColor(0xFF5555))
                        .append("loaded")
                )
                .add(
                    Language.RU,
                    Text.empty().append("Конфиг ").append(Text.empty().append("$cfg ").withColor(0xFF5555))
                        .append("загружен")
                )
            val result = config.load(cfg)
            if (result == "101") {
                message = Language.text()
                    .add(
                        Language.EN,
                        Text.empty().append("Config ").append(Text.empty().append("✖$cfg✖ ").withColor(0xFF5555))
                            .append("doesn't exists")
                    )
                    .add(
                        Language.RU,
                        Text.empty().append("Конфиг ").append(Text.empty().append("✖$cfg✖ ").withColor(0xFF5555))
                            .append("не найден"))
            }
            MessageHandler.send(message)
        } else if (strings[1].contains("save")) {
            val message = Language.text()
                .add(
                    Language.EN,
                    Text.empty().append("Config ").append(Text.empty().append("$cfg ").withColor(0xFF5555))
                        .append("saved")
                )
                .add(
                    Language.RU,
                    Text.empty().append("Конфиг ").append(Text.empty().append("$cfg ").withColor(0xFF5555))
                        .append("сохранен")
                )
            config.save(cfg)
            MessageHandler.send(message)
        }
    }

}