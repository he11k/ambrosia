package rip.ambrosia.menu.creator.buttons

import rip.ambrosia.menu.creator.Button
import rip.ambrosia.menu.creator.ButtonBuilder
import rip.ambrosia.menu.creator.ButtonType
import rip.ambrosia.menu.creator.Frame

class Slider(
    frame: Frame,
    icon: String,
    title: String,
    description: String,
    value: Float,
    contentKey: String,
    @Transient  var minimum: Float,
    @Transient  var maximum: Float,
    @Transient  var increment: Float
) : Button<Float>(frame, icon, title, description, ButtonType.SLIDER,contentKey, value)

class SliderBuilder(
    frame: Frame,
    title: String,
    value: Float,
    var minimum: Float,
    var maximum: Float,
    var increment: Float,
    val contentKey: String,
) : ButtonBuilder<Slider, Float, SliderBuilder>(frame, title, value) {
    override fun build(): Slider {
        val slider = Slider(frame, icon, title,description, value, contentKey, minimum, maximum, increment)
        slider.conditions(activeConditions, renderConditions)
        frame.addButton(slider)
        return slider
    }
}
