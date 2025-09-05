package rip.ambrosia.menu.creator.buttons

import rip.ambrosia.menu.creator.Button
import rip.ambrosia.menu.creator.ButtonBuilder
import rip.ambrosia.menu.creator.ButtonType
import rip.ambrosia.menu.creator.Frame


class MultiSelectbox(
    frame: Frame,
    icon: String,
    title: String,
    description: String,
    val values: Array<String>,
    value: MutableList<String>,
    contentKey: String
) : Button<MutableList<String>>(frame, icon, title,  description, ButtonType.MULTISELECTBOX, contentKey,value) {

    fun isSelected(value: String): Boolean {
        return this.values.contains(value)
    }

}

class MultiSelectboxBuilder(frame: Frame, title: String,  val contentKey: String) :
    ButtonBuilder<MultiSelectbox, MutableList<String>, MultiSelectboxBuilder?>(frame, title, mutableListOf<String>()) {
    var values: MutableList<String> = mutableListOf()

    fun addValue(value: String): MultiSelectboxBuilder {
        values.add(value)
        return this
    }
    fun addSelectedValue(value: String): MultiSelectboxBuilder {
            this.value.add(value)
        values.add(value)
        return this
    }


    override fun build(): MultiSelectbox {
        if(values.isEmpty()) {
            this.title = "Selectbox ERROR"
            values = mutableListOf("Empty")
        }
        val multiSelectbox = MultiSelectbox(
            frame, icon, title, description,
            values.toTypedArray(),value, contentKey
        )
        multiSelectbox.conditions(activeConditions, renderConditions)
        frame.addButton(multiSelectbox)
        return multiSelectbox
    }
}