package rip.ambrosia.menu.creator

import com.google.gson.annotations.Expose
import java.util.function.BooleanSupplier

open class Button<V>(val frame: Frame,
                     val icon: String,
                     @Expose
                      val title: String,
                     val description: String, @Expose val type:ButtonType, val contentKey: String, initialValue: V) {
    @Expose
    var value: V = initialValue
    var activeConditions: List<BooleanSupplier>? = null
    var renderConditions: List<BooleanSupplier>? = null

    fun ableToRender(): Boolean {
        if (renderConditions != null) {
            for (condition in renderConditions!!) {
                if (!condition.asBoolean) {
                    return false
                }
            }
        }
        return true
    }

    fun active(): Boolean {
        if (activeConditions != null) {
            for (condition in activeConditions!!) {
                if (!condition.asBoolean) {
                    return false
                }
            }
        }
        return true
    }

    open fun setValue0(value: V) {
        this.value = value;
    }

    fun conditions(activeConditions: List<BooleanSupplier>?, renderConditions: List<BooleanSupplier>?) {
        this.activeConditions = activeConditions
        this.renderConditions = renderConditions
    }
}

abstract class ButtonBuilder<T, V, B>(protected val frame: Frame, protected var title: String, protected var value: V) {

    protected var description: String = ""
    protected var icon: String = "H"
    protected var activeConditions: MutableList<BooleanSupplier>? = null
    protected var renderConditions: MutableList<BooleanSupplier>? = null

    fun description(description: String): B {
        this.description = description
        return this as B
    }

    fun icon(icon: String): B {
        this.icon = icon
        return this as B
    }

    fun renderCondition(condition: BooleanSupplier): B {
        if (renderConditions == null) {
            renderConditions = ArrayList()
        }
        renderConditions!!.add(condition)
        return this as B
    }

    fun activeCondition(condition: BooleanSupplier): B {
        if (activeConditions == null) {
            activeConditions = ArrayList()
        }
        activeConditions!!.add(condition)
        return this as B
    }

    abstract fun build(): T
}