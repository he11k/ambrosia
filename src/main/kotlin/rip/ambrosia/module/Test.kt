package rip.ambrosia.module

import rip.ambrosia.Ambrosia
import rip.ambrosia.menu.creator.FrameBuilder
import rip.ambrosia.menu.creator.buttons.Checkbox
import rip.ambrosia.menu.creator.buttons.Slider

class Test {

    val mainFrame: FrameBuilder = Ambrosia.menu.primary.main.attackAura.frame("Основное", "main")
    val enable: Checkbox = mainFrame.createCheckbox("Включить", true, "enable").build()
    val distance: Slider = mainFrame.createSlider("Дистанция",5F,0F,10F,0.5F,"distance").build()
    val speed: Slider = mainFrame.createSlider("Скорость",30F,0F,180F,1F,"speed").build()
    val amp: Slider = mainFrame.createSlider("Амплтуда",0.2F,0F,1F,0.1F,"amp").build()
    val condFrame: FrameBuilder = Ambrosia.menu.primary.main.attackAura.frame("Условия", "condition")
    val visualFrame: FrameBuilder = Ambrosia.menu.primary.main.attackAura.frame("Визуализация", "visual")

    val legitMain: FrameBuilder = Ambrosia.menu.primary.main.legit.frame("Основное1", "main")
    val enableL: Checkbox = legitMain.createCheckbox("Включить", true, "enable").build()
    val condLegit: FrameBuilder = Ambrosia.menu.primary.main.legit.frame("Условия1", "condition")
    val visualLegit: FrameBuilder = Ambrosia.menu.primary.main.legit.frame("Визуализация1", "visual")

    val visualESP: FrameBuilder = Ambrosia.menu.primary.visual.world.frame("Обводка", "esp")
    val players: Checkbox = visualESP.createCheckbox("Игроки", true, "players").build()
    val naked: Checkbox = visualESP.createCheckbox("Голые", false, "naked").build()
    val mobs: Checkbox = visualESP.createCheckbox("Мобы", false, "mobs").build()
    val villagers: Checkbox = visualESP.createCheckbox("Жители", true, "villagers").build()
}