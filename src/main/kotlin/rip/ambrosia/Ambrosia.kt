package rip.ambrosia

import com.ibm.icu.text.CaseMap.Fold
import es.boffmedia.mcef.download.MCEFDownloadManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import net.fabricmc.api.ModInitializer
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Identifier
import net.minecraftforge.eventbus.api.BusBuilder
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.lwjgl.glfw.GLFW
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import rip.ambrosia.command.CommandController
import rip.ambrosia.config.ConfigProvider
import rip.ambrosia.event.*
import rip.ambrosia.localserver.MenuHttpServer
import rip.ambrosia.menu.Menu
import rip.ambrosia.module.Test
import rip.ambrosia.util.extensions.mc
import rip.ambrosia.util.web.FolderMover

val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

object Ambrosia : ModInitializer {
    val title: String = "ambrosia"
    val logger: Logger = LoggerFactory.getLogger("ambrosia")
    val eventBus: IEventBus = BusBuilder.builder().build()
    val listener: Listener = Listener()
    var menu: Menu = Menu()
    var counter: Int = 0

    override fun onInitialize() {
        MCEFDownloadManager.sinit()
        Test()
        MenuHttpServer().load()
        logger.info("Ambrosia was loaded")
        eventBus.register(listener)
        CommandController.load()
    }

    class Listener {
        @SubscribeEvent
        fun onKey(event: KeyEvent) {
            if (event.key == GLFW.GLFW_KEY_RIGHT_SHIFT && mc.currentScreen == null) {
//                if(menu == null) {
//                    menu = Menu()
//                }
                mc.setScreen(menu)
            }
        }

        @SubscribeEvent
        fun onTick(event: TickEvent) {
        }

        @SubscribeEvent
        fun onMouseClick(event: MouseClickEvent) {

        }

        @SubscribeEvent
        fun onMouseScroll(event: MouseScrollEvent) {

        }

        @SubscribeEvent
        fun onWindowInit(event: WindowInitEvent) {

        }

        @SubscribeEvent
        fun onFramebufferBlit(event: FramebufferBlitEvent) {

        }
    }
}