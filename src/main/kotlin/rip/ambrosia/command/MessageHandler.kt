package rip.ambrosia.command

import net.minecraft.text.MutableText
import net.minecraft.text.Text
import rip.ambrosia.Ambrosia
import rip.ambrosia.util.language.LanguageString
import rip.ambrosia.util.extensions.mc
import rip.ambrosia.util.language.LanguageText
import java.awt.Color
import java.util.*
import kotlin.math.roundToInt

object MessageHandler {

    private fun getPrefix(): MutableText {
        return Text.empty()
            .append(Text.empty().styled { s -> s.withColor(Color.HSBtoRGB(Random().nextFloat(), 1f, 1f)) }
                .withColor(0xFF2323).append("["))
            .append(Text.empty().withColor(0xFF5555).append(Ambrosia.title))
            .append(Text.empty().withColor(0xFF2323).append("] "))
    }

    fun send(text: Text) {
        mc.inGameHud.chatHud.addMessage(getPrefix().append(Text.empty().append(text).withColor(0xFFFFFF)))
    }

    fun send(string: String) {
        mc.inGameHud.chatHud.addMessage(getPrefix().append(Text.empty().append(string).withColor(0xFFFFFF)))
    }

    fun send(string: LanguageString) {
        send(string.get())
    }

    fun send(text: LanguageText) {
        send(text.get())
    }

}