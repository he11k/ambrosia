package rip.ambrosia.browser

import es.boffmedia.mcef.MCEF
import es.boffmedia.mcef.MCEFBrowser
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.AbstractTexture
import net.minecraft.util.Identifier
import rip.ambrosia.util.extensions.mc

object BrowserManager {
    var browser: MCEFBrowser? = null
    val texture: Identifier = Identifier.of("ambrosia", "browser/tab")
    val drawOffset: Int = 0

    fun init() {
        if (browser == null) {
            val url = "http://localhost:5173/"
//            val indexFile = File(ConfigProvider.webFolder, "index.html")
//            val url = indexFile.toURI().toURL().toString()
            val transparent = true
            browser = MCEF.createBrowser(url, transparent)
            resizeBrowser(
                mc.window.scaledWidth,
                mc.window.scaledHeight
            )
            mc.textureManager.registerTexture(texture, object : AbstractTexture() {
                override fun getGlId(): Int {
                    return browser!!.renderer.textureID
                }
            })
        }
    }

    fun scaleX(x: Double): Int {
        return ((x - drawOffset * 2) * mc.window.scaleFactor).toInt()
    }

    fun scaleY(y: Double): Int {
        return ((y - drawOffset * 2) * mc.window.scaleFactor).toInt()
    }

    fun resizeBrowser(width: Int, height: Int) {
        if (width > 100 && height > 100) {
            browser!!.resize(scaleX(width.toDouble()), scaleY(height.toDouble()))
        }
    }

    fun stop() {
        browser!!.close()
    }

}