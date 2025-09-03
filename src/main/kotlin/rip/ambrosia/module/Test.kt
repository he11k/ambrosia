package rip.ambrosia.module

import rip.ambrosia.Ambrosia
import rip.ambrosia.menu.creator.FrameBuilder
import rip.ambrosia.menu.creator.buttons.Checkbox
import rip.ambrosia.menu.creator.buttons.Selectbox
import rip.ambrosia.menu.creator.buttons.Slider
import rip.ambrosia.menu.creator.condition.CheckboxCondition
import rip.ambrosia.menu.creator.condition.Condition

class Test {

    val mainFrame: FrameBuilder = Ambrosia.menu.primary.main.attackAura.frame("Основное", "main")
    val enable: Checkbox = mainFrame.createCheckbox("Включить", true, "enable").build()
    val mode: Selectbox = mainFrame.createSelectbox("Режим",  "mode")
        .addValue("Дистанция").addSelectedValue("Здоровье").addValue("Поле-Зрения").build()
    val distance: Slider = mainFrame.createSlider("Дистанция",5F,0F,10F,0.5F,"distance").renderCondition(
        CheckboxCondition(enable)).build()
    val speed: Slider = mainFrame.createSlider("Скорость",30F,0F,180F,1F,"speed").build()
    val amp: Slider = mainFrame.createSlider("Амплитуда",0.2F,0F,1F,0.1F,"amp").build()
    val condFrame: FrameBuilder = Ambrosia.menu.primary.main.attackAura.frame("Условия", "condition")
    val visualFrame: FrameBuilder = Ambrosia.menu.primary.main.attackAura.frame("Визуализация", "visual")

    val legitMain: FrameBuilder = Ambrosia.menu.primary.main.legit.frame("Основное1", "main")
    val enableL: Checkbox = legitMain.createCheckbox("Включить", true, "enable").build()
    val length: Slider = legitMain.createSlider("Длина",5F,0F,10F,0.5F,"length").activeCondition(CheckboxCondition(enableL)).build()
    val condLegit: FrameBuilder = Ambrosia.menu.primary.main.legit.frame("Условия1", "condition")
    val players1: Checkbox = condLegit.createCheckbox("Обработать", true, "handle").activeCondition(CheckboxCondition(enableL))!!.build()
    val naked1: Checkbox = condLegit.createCheckbox("Выебать", false, "vieb").activeCondition(CheckboxCondition(enableL))!!.build()
    val mobs1: Checkbox = condLegit.createCheckbox("Пенить", false, "peen").activeCondition(CheckboxCondition(enableL))!!.build()
    val villagers1: Checkbox = condLegit.createCheckbox("Активировать", true, "activ").activeCondition(CheckboxCondition(enableL))!!.build()
    val visualLegit: FrameBuilder = Ambrosia.menu.primary.main.legit.frame("Визуализация1", "visual")

    val visualESP: FrameBuilder = Ambrosia.menu.primary.visual.world.frame("Обводка", "esp")
    val distanceAv: Slider = visualESP.createSlider("Попка",0F,-10F,10F,0.001F,"popka").build()
    val zalupa: Slider = visualESP.createSlider("Залупка",5F,5F,20F,0.001F,"zalupka").build()

    val players: Checkbox = visualESP.createCheckbox("Игроки", true, "players").build()
    val naked: Checkbox = visualESP.createCheckbox("Голые", false, "naked").build()
    val mobs: Checkbox = visualESP.createCheckbox("Мобы", false, "mobs").build()
    val villagers: Checkbox = visualESP.createCheckbox("Жители", true, "villagers").build()
}