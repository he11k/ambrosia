package rip.ambrosia.util.web

import net.minecraft.resource.Resource
import net.minecraft.util.Identifier
import rip.ambrosia.util.extensions.mc
import java.io.File
import java.io.FileOutputStream

object FolderMover {
    fun moveFolderFromResources(identifier: Identifier, targetDir: File) {
        println("ПЫТАЮСЬ СКОПИРОВАТЬ")
        val resourceManager = mc.resourceManager // mc - это MinecraftClient

        // Убедимся, что целевая директория существует
        targetDir.mkdirs()

        val pathPrefix = if (identifier.path.endsWith("/")) identifier.path else "${identifier.path}/"

        // Используем findResources для более контролируемого поиска
        // или getAllResources для получения всех ресурсов по этому идентификатору
        // и затем фильтруем.
        // Для примера, используем findAllResources, как у тебя, но с доп. обработкой
        val allResources: Map<Identifier, List<Resource>> = resourceManager.findAllResources(identifier.path) { true }

        for ((resourceId, resourceList) in allResources) {
            // Проверяем, что ресурс действительно начинается с нашего идентификатора
            if (!resourceId.path.startsWith(pathPrefix)) {
                continue
            }

            val relativePath = resourceId.path.removePrefix(pathPrefix)
            if (relativePath.isEmpty()) {
                // Это сам корневой идентификатор, пропускаем, если это папка
                continue
            }

            val outFile = File(targetDir, relativePath)
            outFile.parentFile?.mkdirs()

            // Берем последний ресурс из списка, так как он обычно имеет наивысший приоритет
            resourceList.lastOrNull()?.let { res ->
                res.inputStream.use { input ->
                    FileOutputStream(outFile).use { output ->
                        input.copyTo(output)
                    }
                }
                println("Скопирован файл: ${resourceId.path} в ${outFile.absolutePath}")
            }
        }
    }
}