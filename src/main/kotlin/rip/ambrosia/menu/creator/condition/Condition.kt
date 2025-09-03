package rip.ambrosia.menu.creator.condition

import rip.ambrosia.menu.Subcategory
import rip.ambrosia.menu.creator.Button

open class Condition<T, K>(val button: K) {
    fun getValue(): T {
        return (button as Button<T>).value
    }

    fun getKey(): String {
        return (button as Button<T>).contentKey
    }

    fun getFrameKey(): String {
        return (button as Button<T>).frame.contentKey
    }

    fun getSubcategoryKey(): String {
        return ((button as Button<T>).frame.frameHolder as Subcategory).key
    }

    fun getCategoryKey(): String {
        return ((button as Button<T>).frame.frameHolder as Subcategory).parent.key
    }

    fun getPath(): String {
        return getCategoryKey() + "." + getSubcategoryKey() + "." + getFrameKey() + "." + getKey()
    }
}