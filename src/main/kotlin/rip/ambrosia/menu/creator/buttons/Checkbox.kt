package rip.ambrosia.menu.creator.buttons

import com.google.gson.annotations.Expose
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import rip.ambrosia.menu.creator.*


class Checkbox(
    frame: Frame,
    icon: String,
    title: String,
    description: String,
    value: Boolean,
    contentKey: String,
    @Expose
    var key: Int = -1
) : Button<Boolean>(frame, icon, title,  description, ButtonType.CHECKBOX, contentKey,value) {

    fun updateKey(key: Int) {
        if (this.key == key) {
            this.value = !value
            val isNoname = title == "Включить" || title == "Enable"
            MinecraftClient.getInstance().player!!.sendMessage(
                Text.of((if (isNoname) frame.title else title) + ":  $value"), true
            )
        }
    }

    override fun setValue0(value: Boolean) {
        this.value = value
    }
}

class CheckboxBuilder(frame: Frame, title: String, value: Boolean, val contentKey: String) :
    ButtonBuilder<Checkbox, Boolean, CheckboxBuilder?>(frame, title, value) {
    var bindable: Boolean = false
    var key: Int = -1

    fun bindable(): CheckboxBuilder {
        this.bindable = true
        return this
    }

    fun key(key: Int): CheckboxBuilder {
        this.key = key
        return this
    }


    override fun build(): Checkbox {
        val checkbox = Checkbox(
            frame, icon, title, description,
            value, contentKey , key
        )
        checkbox.conditions(activeConditions, renderConditions)
        frame.addButton(checkbox)
        return checkbox
    }
}