package rip.ambrosia.menu.creator

import com.google.gson.annotations.Expose

class Frame(@Expose internal val title: String, val contentKey: String) {
    @Expose
    internal var buttons: MutableList<Button<*>> = ArrayList()

    private fun resetButtons(buttons: MutableList<Button<*>>) {
        this.buttons = buttons
    }

    fun getButton(title: String, type: ButtonType): Button<Any>? {
        for (button in buttons) {
            if (button.title == title && button.type == type) {
                return button as Button<Any>
            }
        }
        return null
    }
    fun getButtonFromKey(key: String, type: ButtonType): Button<Any>? {
        for (button in buttons) {
            if (button.contentKey == key && button.type == type) {
                return button as Button<Any>
            }
        }
        return null
    }


    fun addButton(button: Button<*>) {
        buttons.add(button)
    }
}