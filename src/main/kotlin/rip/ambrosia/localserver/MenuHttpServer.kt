package rip.ambrosia.localserver

import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import rip.ambrosia.Ambrosia
import rip.ambrosia.menu.Category
import rip.ambrosia.menu.MainWindow
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import kotlin.collections.map

class MenuHttpServer {
    @Throws(IOException::class)
    fun load() {
        val server = HttpServer.create(InetSocketAddress(9123), 0)

        server.createContext("/categories", CategoriesHandler())

        server.executor = null
        server.start()
        println("Local-Server started at http://localhost:9123")
    }
    internal class CategoriesHandler : HttpHandler {
        data class JSSubcategory(val name: String, val icon: String)
        data class JSCategory(val name: String, val icon: String, val subcategories: List<JSSubcategory>)

        @Throws(IOException::class)
        override fun handle(exchange: HttpExchange) {

            exchange.responseHeaders.add("Access-Control-Allow-Origin", "*")
            exchange.responseHeaders.add("Access-Control-Allow-Methods", "GET, OPTIONS")
            exchange.responseHeaders.add("Access-Control-Allow-Headers", "Content-Type")

            if ("GET" == exchange.requestMethod) {
                val jsCategories = Ambrosia.menu.primary.categories[Category.Group.CATEGORIES]?.map { cat ->
                    JSCategory(name = cat.title, icon = cat.icon, subcategories = cat.subcategories.map { sub ->
                        JSSubcategory(name = sub.title, icon = sub.icon)
                    })
                } ?: emptyList()

                val json = Gson().toJson(jsCategories)
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
