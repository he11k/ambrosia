package rip.ambrosia.menu.creator.buttons


import com.google.gson.annotations.Expose
import rip.ambrosia.menu.creator.*


class Selectbox(
    frame: Frame,
    icon: String,
    title: String,
    description: String,
    val values: Array<String>,
    value: String,
    contentKey: String
) : Button<String>(frame, icon, title,  description, ButtonType.SELECTBOX, contentKey,value) {

    fun isSelected(value: String): Boolean {
        return value == this.value
    }

}

class SelectboxBuilder(frame: Frame, title: String,  val contentKey: String) :
    ButtonBuilder<Selectbox, String, SelectboxBuilder?>(frame, title, "Empty") {
    var values: MutableList<String> = mutableListOf()
    var added: Boolean = false

    fun addValue(value: String): SelectboxBuilder {
       values.add(value)
        return this
    }
    fun addSelectedValue(value: String): SelectboxBuilder {
        if(!added) {
            added = true
            this.value = value
        }
        values.add(value)
        return this
    }


    override fun build(): Selectbox {
        if(values.isEmpty()) {
            this.title = "Selectbox ERROR"
            values = mutableListOf("Empty")
        }
        val selectbox = Selectbox(
            frame, icon, title, description,
            values.toTypedArray(),value, contentKey
        )
        selectbox.conditions(activeConditions, renderConditions)
        frame.addButton(selectbox)
        return selectbox
    }
}