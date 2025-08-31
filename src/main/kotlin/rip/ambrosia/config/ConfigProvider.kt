package rip.ambrosia.config

import rip.ambrosia.Ambrosia
import rip.ambrosia.util.extensions.mc
import java.io.File

object ConfigProvider {
    val rootFolder = File(
        mc.runDirectory, Ambrosia.title
    ).apply {
        if (!exists()) {
            mkdir()
        }
    }
    val configFolder = File(
        rootFolder, "configs"
    ).apply {
        if (!exists()) {
            mkdir()
        }
    }
    val webFolder = File(
        rootFolder, "browser"
    ).apply {
        if (!exists()) {
            mkdir()
        }
    }

}