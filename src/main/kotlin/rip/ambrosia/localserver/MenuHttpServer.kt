package rip.ambrosia.localserver

import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import rip.ambrosia.Ambrosia
import rip.ambrosia.menu.Category
import rip.ambrosia.menu.MainWindow
import rip.ambrosia.menu.creator.buttons.Checkbox
import rip.ambrosia.menu.creator.buttons.Slider
import rip.ambrosia.module.Test
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import kotlin.collections.forEach
import kotlin.collections.map

class MenuHttpServer {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Ambrosia.menu
            Test()
            MenuHttpServer().load()
        }
    }

    @Throws(IOException::class)
    fun load() {
        val server = HttpServer.create(InetSocketAddress(9123), 0)

        server.createContext("/categories", CategoriesHandler())

        server.executor = null
        server.start()
        println("Local-Server started at http://localhost:9123")
    }

    internal class CategoriesHandler : HttpHandler {
        data class JSSubcategory(val name: String, val icon: String, val frames: List<JSFrame>)
        data class JSCategory(val subcategories: List<JSSubcategory>, val name: String, val icon: String)
        sealed class JSButton {
            abstract val icon: String
            abstract val name: String
            abstract val description: String
            abstract val type: String
        }

        data class JSCheckbox(
            override val icon: String,
            override val name: String,
            override val description: String,
            val value: Boolean,
            override val type: String = "CHECKBOX"
        ) : JSButton()

        data class JSSlider(
            override val icon: String,
            override val name: String,
            override val description: String,
            val value: Float,
            override val type: String = "SLIDER"
        ) : JSButton()

        data class JSFrame(val name: String, val buttons: List<JSButton>)

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
                            subcategories = cat.subcategories.map { sub ->
                                JSSubcategory(name = sub.title, icon = sub.icon, frames = sub.frames.map { f ->
                                    JSFrame(name = f.title, buttons = f.buttons.map { b ->
                                        when (b) {
                                            is Checkbox -> {
                                                JSCheckbox(
                                                    icon = b.icon,
                                                    name = b.title,
                                                    description = b.description,
                                                    value = b.value
                                                )
                                            }

                                            is Slider -> {
                                                JSSlider(
                                                    icon = b.icon,
                                                    name = b.title,
                                                    description = b.description,
                                                    value = b.value
                                                )
                                            }

                                            else -> {
                                                JSCheckbox(
                                                    icon = b.icon,
                                                    name = b.title,
                                                    description = b.description,
                                                    value = true
                                                )
                                            }
                                        }
                                    })
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
