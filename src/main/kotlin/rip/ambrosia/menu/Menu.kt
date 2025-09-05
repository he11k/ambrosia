package rip.ambrosia.menu

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import rip.ambrosia.browser.BrowserManager
import rip.ambrosia.browser.BrowserManager.browser
import rip.ambrosia.browser.BrowserManager.resizeBrowser
import rip.ambrosia.util.extensions.mc
import rip.ambrosia.util.render.AmbrosiaRenderLayers
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.*
import javax.imageio.ImageIO
import kotlin.math.roundToInt


class Menu : Screen(Text.literal("menu")) {
    val primary: MainWindow = MainWindow()


    override fun init() {
        super.init()
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
//        super.render(context, mouseX, mouseY, delta)
        RenderSystem.disableDepthTest()
        val x1 = BrowserManager.drawOffset
        val x2 = width - BrowserManager.drawOffset - x1
        val y1 = BrowserManager.drawOffset
        val y2 = height - BrowserManager.drawOffset - y1
        context.drawTexture(
            AmbrosiaRenderLayers::getBlurredTextureLayer, BrowserManager.texture, x1, y1, 0f, 0f, x2,
            y2, x2, y2
        )
        RenderSystem.enableDepthTest()
    }

    override fun tick() {
        super.tick()

    }

    private fun mouseX(x: Double): Int {
        return ((x - BrowserManager.drawOffset) * mc.window.scaleFactor).roundToInt()
    }

    private fun mouseY(y: Double): Int {
        return ((y - BrowserManager.drawOffset) * mc.window.scaleFactor).roundToInt()
    }

    override fun resize(minecraft: MinecraftClient?, i: Int, j: Int) {
        super.resize(minecraft, i, j)
        resizeBrowser(width, height)
    }

        override fun close() {
        super.close()
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        BrowserManager.browser!!.sendMousePress(mouseX(mouseX), mouseY(mouseY), button)
        BrowserManager.browser!!.setFocus(true)
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        BrowserManager.browser!!.sendMouseRelease(mouseX(mouseX), mouseY(mouseY), button)
        BrowserManager.browser!!.setFocus(true)
        return super.mouseReleased(mouseX, mouseY, button)
    }

    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        BrowserManager.browser!!.sendMouseMove(mouseX(mouseX), mouseY(mouseY))
        super.mouseMoved(mouseX, mouseY)
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, dragX: Double, dragY: Double): Boolean {
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY)
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
        BrowserManager.browser!!.sendMouseWheel(mouseX(mouseX), mouseY(mouseY), scrollY, 0)
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if(keyCode == GLFW.GLFW_KEY_R) {
            BrowserManager.browser!!.reload()
        }
        BrowserManager.browser!!.sendKeyPress(keyCode, scanCode.toLong(), modifiers)
        BrowserManager.browser!!.setFocus(true)
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        BrowserManager.browser!!.sendKeyRelease(keyCode, scanCode.toLong(), modifiers)
        BrowserManager.browser!!.setFocus(true)
        return super.keyReleased(keyCode, scanCode, modifiers)
    }

    override fun charTyped(codePoint: Char, modifiers: Int): Boolean {
        if (codePoint == 0.toChar()) return false
        BrowserManager.browser!!.sendKeyTyped(codePoint, modifiers)
        BrowserManager.browser!!.setFocus(true)
        return super.charTyped(codePoint, modifiers)
    }
}