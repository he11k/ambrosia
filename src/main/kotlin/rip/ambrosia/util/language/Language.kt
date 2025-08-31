package rip.ambrosia.util.language

import net.minecraft.text.Text
import java.util.*

class LanguageText {
    val languagesMap: MutableMap<Language, Text> = EnumMap(Language::class.java)

    fun add(lang: Language, text: Text): LanguageText {
        languagesMap[lang] = text
        return this
    }

    fun get(): Text {
        if(languagesMap.containsKey(Language.currentLanguage)) {
            return languagesMap[Language.currentLanguage]!!
        }
        return Text.of("")
    }
}

class LanguageString {
    val languagesMap: MutableMap<Language, String> = EnumMap(Language::class.java)

    fun add(lang: Language, string: String): LanguageString {
        languagesMap[lang] = string
        return this
    }
    fun get(): String {
        if(languagesMap.containsKey(Language.currentLanguage)) {
            return languagesMap[Language.currentLanguage]!!
        }
        return ""
    }
}

enum class Language(val title: String) {
    EN("English"), RU("Русский");

    fun matchWith(string: String): Boolean {
        return title.equals(string, true) || name.equals(string, true)
    }

    companion object {
        var currentLanguage: Language = RU

        fun containsLanguage(string: String): Boolean {
            for (entry in entries) {
                if (entry.matchWith(string)) {
                    return true
                }
            }
            return false
        }

        fun fromTitle(string: String): Language {
            for (entry in entries) {
                if (entry.matchWith(string)) {
                    return entry
                }
            }
            return RU
        }

        fun text(): LanguageText {
            return LanguageText()
        }
        fun string(): LanguageString {
            return LanguageString()
        }
    }
}