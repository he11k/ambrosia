package rip.ambrosia.command

import net.minecraft.text.Text
import rip.ambrosia.command.impl.ConfigCommand
import rip.ambrosia.command.impl.HelpCommand
import rip.ambrosia.command.impl.LanguageCommand
import rip.ambrosia.util.language.Language
import java.util.ArrayList

object CommandController {
    val prefix = "."
    val commands: MutableList<Command> = ArrayList<Command>()

    fun load(){
        commands.addAll(listOf(
            ConfigCommand,
            LanguageCommand,
            HelpCommand
        ))
    }

    fun contains(string: String):Boolean {
        for (command in commands) {
            if(string.contains(command.name)) {
                return true
            }
        }
        return false
    }

    fun validate(string: String): Boolean {
        val valid = contains(string.split(" ")[0]);
        if(!valid) {
            val errorLang = Language.text()
                .add(Language.EN, Text.empty().append("Invalid command, try - ").append(Text.empty().withColor(0xFF5555).append("${prefix}${HelpCommand.name}")))
                .add(Language.RU, Text.empty().append("Неверная команда, попробуйте - ").append(Text.empty().withColor(0xFF5555).append("${prefix}${HelpCommand.name}")))
            MessageHandler.send(errorLang.get())
        }
        return valid;
    }

}