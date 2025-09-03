package rip.ambrosia.localserver

import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import rip.ambrosia.Ambrosia
import rip.ambrosia.localserver.MenuHttpServer.UpdateButtonHandler.UpdateRequest
import rip.ambrosia.menu.Category
import rip.ambrosia.menu.creator.Button
import rip.ambrosia.menu.creator.ButtonType
import rip.ambrosia.menu.creator.buttons.Checkbox
import rip.ambrosia.menu.creator.buttons.Selectbox
import rip.ambrosia.menu.creator.buttons.Slider
import rip.ambrosia.menu.creator.condition.CheckboxCondition
import rip.ambrosia.menu.creator.condition.Condition
import rip.ambrosia.menu.creator.condition.SelectboxCondition
import rip.ambrosia.module.Test
import rip.ambrosia.module.Test2
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import kotlin.collections.map

class MenuHttpServer {
    sealed class JSButton {
        abstract val icon: String
        abstract val name: String
        abstract val description: String
        abstract val type: String
        abstract val contentKey: String
        abstract val active: Boolean
        abstract val activeConditions: Array<JSCondition>
        abstract val render: Boolean
        abstract val renderConditions: Array<JSCondition>
    }

    sealed class JSCondition {
        abstract val contentKey: String
        abstract val type: String
    }

    data class JSCheckboxCondition(override val contentKey: String, val value: Boolean,override val type: String = "CHECKBOX") : JSCondition()
    data class JSSelectboxCondition(override val contentKey: String, val value: String,override val type: String = "SELECTBOX") : JSCondition()
    data class JSCheckbox(
        override val icon: String,
        override val name: String,
        override val description: String,
        val value: Boolean,
        override val active: Boolean,
        override val activeConditions: Array<JSCondition>,
        override val render: Boolean,
        override val renderConditions: Array<JSCondition>,
        override val type: String = "CHECKBOX",
        override val contentKey: String
    ) : JSButton()

    data class JSSelectbox(
        override val icon: String,
        override val name: String,
        override val description: String,
        val value: String,
        val values: Array<String>,
        override val active: Boolean,
        override val activeConditions: Array<JSCondition>,
        override val render: Boolean,
        override val renderConditions: Array<JSCondition>,
        override val type: String = "SELECTBOX",
        override val contentKey: String
    ) : JSButton()

    data class JSSlider(
        override val icon: String,
        override val name: String,
        override val description: String,
        val value: Float,
        val minimum: Float,
        val maximum: Float,
        val increment: Float,
        override val active: Boolean,
        override val activeConditions: Array<JSCondition>,
        override val render: Boolean,
        override val renderConditions: Array<JSCondition>,
        override val type: String = "SLIDER",
        override val contentKey: String
    ) : JSButton()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Ambrosia.menu
            Test()
            Test2()
            MenuHttpServer().load()
        }

        fun getConditions(conditions: List<Condition<*,*>>?): Array<JSCondition> {
            return conditions?.map { cond ->
                when (cond) {
                    is CheckboxCondition -> {
                        JSCheckboxCondition(cond.getPath(), cond.getValue())
                    }

                    is SelectboxCondition -> {
                        JSSelectboxCondition(cond.getPath(), cond.getValue())
                    }

                    else -> {
                        JSCheckboxCondition("", true)
                    }
                }
            }?.toTypedArray() ?: emptyArray()
        }

        fun getButtons(buttons: MutableList<Button<*>>): List<JSButton> {
            return buttons.map { b ->
                when (b) {
                    is Checkbox -> {
                        JSCheckbox(
                            icon = b.icon,
                            name = b.title,
                            description = b.description,
                            value = b.value,
                            contentKey = b.contentKey,
                            activeConditions = getConditions(b.activeConditions),
                            active = true,
                            renderConditions = getConditions(b.renderConditions),
                            render = true,
                        )
                    }

                    is Slider -> {
                        JSSlider(
                            icon = b.icon,
                            name = b.title,
                            description = b.description,
                            value = b.value,
                            minimum = b.minimum,
                            maximum = b.maximum,
                            increment = b.increment,
                            contentKey = b.contentKey,
                            activeConditions = getConditions(b.activeConditions),
                            active = true,
                            renderConditions = getConditions(b.renderConditions),
                            render = true,
                        )
                    }

                    is Selectbox -> {
                        JSSelectbox(
                            icon = b.icon,
                            name = b.title,
                            description = b.description,
                            value = b.value,
                            values = b.values,
                            contentKey = b.contentKey,
                            activeConditions = getConditions(b.activeConditions),
                            active = true,
                            renderConditions = getConditions(b.renderConditions),
                            render = true,
                        )
                    }

                    else -> {
                        JSCheckbox(
                            icon = b.icon,
                            name = "Error value ${b.type.name}",
                            description = "Error value ${b.type.name}",
                            value = true,
                            contentKey = b.contentKey,
                            activeConditions = getConditions(b.activeConditions),
                            active = true,
                            renderConditions = getConditions(b.renderConditions),
                            render = true,
                        )
                    }
                }
            }.toList()
        }
    }

    @Throws(IOException::class)
    fun load() {
        val server = HttpServer.create(InetSocketAddress(9123), 0)

        server.createContext("/menu", LoadHandler())
        server.createContext("/update-button", UpdateButtonHandler())

        server.executor = null
        server.start()
        println("Local-Server started at http://localhost:9123")
    }

    internal class UpdateButtonHandler : HttpHandler {
        data class UpdateRequest(
            val category: String,
            val subcategory: String,
            val frame: String,
            val name: String,
            val type: String,
            val value: Any
        )

        override fun handle(exchange: HttpExchange) {
            exchange.responseHeaders.add("Access-Control-Allow-Origin", "*")
            exchange.responseHeaders.add("Access-Control-Allow-Methods", "POST, OPTIONS")
            exchange.responseHeaders.add("Access-Control-Allow-Headers", "Content-Type")

            if (exchange.requestMethod == "OPTIONS") {
                exchange.sendResponseHeaders(200, -1)
                return
            }

            if (exchange.requestMethod != "POST") {
                exchange.sendResponseHeaders(405, -1)
                return
            }

            val body = exchange.requestBody.bufferedReader().use { it.readText() }
            try {
                val request = Gson().fromJson(body, UpdateRequest::class.java)

                val category = Ambrosia.menu.primary.getCategoryFromKey(request.category)
                val subcategory = category!!.getCategoryFromKey(request.subcategory)
                val frame = subcategory!!.getFrameFromKey(request.frame)
                val button = frame!!.getButtonFromKey(request.name, ButtonType.valueOf(request.type))

                if (button is Checkbox) {
                    button.value = request.value as Boolean
                } else if (button is Slider) {
                    button.value = (request.value as Double).toFloat()
                } else if (button is Selectbox) {
                    button.value = request.value as String
                }

                exchange.sendResponseHeaders(200, -1)
            } catch (e: Exception) {
                e.printStackTrace()
                exchange.sendResponseHeaders(500, -1)
            }
        }
    }


    internal class LoadHandler : HttpHandler {
        data class JSSubcategory(val name: String, val icon: String, val frames: List<JSFrame>, val contentKey: String)
        data class JSCategory(
            val subcategories: List<JSSubcategory>,
            val name: String,
            val icon: String,
            val contentKey: String
        )


        data class JSFrame(val name: String, val buttons: List<JSButton>, val contentKey: String)

        @Throws(IOException::class)
        override fun handle(exchange: HttpExchange) {

            exchange.responseHeaders.add("Access-Control-Allow-Origin", "*")
            exchange.responseHeaders.add("Access-Control-Allow-Methods", "GET, OPTIONS")
            exchange.responseHeaders.add("Access-Control-Allow-Headers", "Content-Type")
            if ("GET" == exchange.requestMethod) {
                val categories: Map<String, List<JSCategory>> = listOf(
                    Category.Group.CATEGORIES to "CATEGORIES",
                    Category.Group.OTHER to "OTHER"
                ).associate { (group, name) ->
                    name to (Ambrosia.menu.primary.categories[group]?.map { cat ->
                        JSCategory(
                            name = cat.title,
                            icon = cat.icon,
                            contentKey = cat.key,
                            subcategories = cat.subcategories.map { sub ->
                                JSSubcategory(
                                    name = sub.title,
                                    icon = sub.icon,
                                    contentKey = sub.key,
                                    frames = sub.frames.map { f ->
                                        JSFrame(
                                            name = f.title,
                                            contentKey = f.contentKey,
                                            buttons = getButtons(f.buttons)
                                        )
                                    })
                            }
                        )
                    } ?: emptyList())
                }

                val json = Gson().toJson(categories)
                exchange.responseHeaders.add("Content-Type", "application/json; charset=UTF-8")
                exchange.sendResponseHeaders(200, json.toByteArray(StandardCharsets.UTF_8).size.toLong())

                exchange.responseBody.use { os ->
                    os.write(json.toByteArray(StandardCharsets.UTF_8))
                }
            } else {
                exchange.sendResponseHeaders(405, -1)
            }
        }
    }


}
