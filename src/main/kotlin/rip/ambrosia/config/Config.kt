package rip.ambrosia.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.LinkedTreeMap
import rip.ambrosia.Ambrosia
import rip.ambrosia.menu.Category
import rip.ambrosia.menu.creator.ButtonType
import rip.ambrosia.menu.creator.buttons.*
import java.io.File


class Config {

    fun load(cfg: String):String {
        val file = File(ConfigProvider.configFolder, cfg)
        if(!ConfigProvider.configFolder.exists()) {
            ConfigProvider.configFolder.mkdir()
        }
        if(!file.exists()) {
            return "101"
        }
        val json = file.readText(Charsets.UTF_8)
        println(json)
        val gson = Gson()
        val f = gson.fromJson(json, Ambrosia.menu!!.primary.javaClass)
        if (f.categories[Category.Group.CATEGORIES] != null) {
            for (category in f.categories[Category.Group.CATEGORIES]!!) {
                for (subcategory in category.subcategories) {
                    for (frame in subcategory.frames) {
                        for (button in frame.buttons) {
                            if (Ambrosia.menu!!.primary.getCategory(category.title) == null) {
                                continue
                            }
                            if (Ambrosia.menu!!.primary.getCategory(category.title)!!
                                    .getCategory(subcategory.title) == null
                            ) {
                                continue; }
                            if (Ambrosia.menu!!.primary.getCategory(category.title)!!.getCategory(subcategory.title)!!
                                    .getFrame(frame.title) == null
                            ) {
                                continue; }
                            if (Ambrosia.menu!!.primary.getCategory(category.title)!!.getCategory(subcategory.title)!!
                                    .getFrame(frame.title)!!.getButton(button.title, button.type) == null
                            ) {
                                continue; }

                            val currentButton =
                                Ambrosia.menu!!.primary.getCategory(category.title)!!.getCategory(subcategory.title)!!
                                    .getFrame(frame.title)!!.getButton(button.title, button.type)!!
                            if (button.type == ButtonType.CHECKBOX && currentButton.type == ButtonType.CHECKBOX) {
                                currentButton.value = button.value as Boolean
                            } else if (button.type == ButtonType.SLIDER && currentButton.type == ButtonType.SLIDER) {
                                currentButton.value = button.value as Double
                            } else if (button.type == ButtonType.COLORPICKER && currentButton.type == ButtonType.COLORPICKER) {
                                currentButton.value = button.value as Int
                            } else if (button.type == ButtonType.MULTISELECTBOX && currentButton.type == ButtonType.MULTISELECTBOX) {
//                                currentButton.value = button.value as MutableList<*>
                            } else if (button.type == ButtonType.MULTISLIDER && currentButton.type == ButtonType.MULTISLIDER) {
                                val v = (button.value as LinkedTreeMap<String, Float>)
                                (currentButton.value as MultiSliderValue).minimum = v["minimum"]!!
                                (currentButton.value as MultiSliderValue).maximum = v["maximum"]!!
                            } else if (button.type == ButtonType.SELECTBOX && currentButton.type == ButtonType.SELECTBOX) {
//                                currentButton.value = button.value as Enum<*>
                            }
                        }
                    }
                }
            }
        }
        return "OK"
    }


    fun save(cfg: String) {
        if(!ConfigProvider.configFolder.exists()) {
            ConfigProvider.configFolder.mkdir()
        }
        val gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        File(ConfigProvider.configFolder, cfg).writeText(gson.toJson(Ambrosia.menu!!.primary), Charsets.UTF_8)
    }
}