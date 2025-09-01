package rip.ambrosia.menu.creator.buttons

import com.google.gson.annotations.Expose
import rip.ambrosia.menu.creator.Button
import rip.ambrosia.menu.creator.ButtonBuilder
import rip.ambrosia.menu.creator.ButtonType
import rip.ambrosia.menu.creator.Frame

class MultiSlider(
    frame: Frame,
    icon: String,
    title: String,
    description: String,
    value: MultiSliderValue,
    contentKey: String,
    var minimum: Float,
    var maximum: Float,
    var increment: Float
) :
    Button<MultiSliderValue>(frame, icon, title, description, ButtonType.MULTISLIDER,contentKey,value) {
    fun setMinimumValue(value: Float) {
        this.value.minimum = value
    }

    fun setMaximumValue(value: Float) {
        this.value.maximum = value
    }

    fun random(): Float =
        this.value.minimum + (this.value.maximum - this.value.minimum) * kotlin.random.Random.nextFloat()
}

class MultiSliderBuilder(
    frame: Frame,
    title: String,
    minimumValue: Float,
    maximumValue: Float,
    var minimum: Float,
    var maximum: Float,
    var increment: Float,
    val contentKey: String
) :
    ButtonBuilder<MultiSlider, MultiSliderValue, MultiSliderBuilder>(
        frame,
        title, MultiSliderValue(minimumValue, maximumValue)
    ) {
    override fun build(): MultiSlider {
        val slider = MultiSlider(frame, icon, title, description, value,contentKey, minimum, maximum, increment)
        slider.conditions(activeConditions, renderConditions)
        frame.addButton(slider)
        return slider
    }
}

data class MultiSliderValue(@Expose var minimum: Float,@Expose var maximum: Float)