package rip.ambrosia.menu.creator

import rip.ambrosia.menu.creator.buttons.*

class FrameBuilder(title: String) {
    val frame: Frame = Frame(title)

    fun createCheckbox(title: String, value: Boolean): CheckboxBuilder {
        return CheckboxBuilder(frame, title, value)
    }

    fun createSlider(
        title: String,
        value: Float,
        minimum: Float,
        maximum: Float,
        increment: Float
    ): SliderBuilder {
        return SliderBuilder(frame, title, value, minimum, maximum, increment)
    }

    fun createMultiSlider(
        title: String,
        minimumValue: Float,
        maximumValue: Float,
        minimum: Float,
        maximum: Float,
        increment: Float
    ): MultiSliderBuilder {
        return MultiSliderBuilder(frame, title, minimumValue, maximumValue, minimum, maximum, increment)
    }

    fun createColorPicker(title: String, value: Int, vararg modes: ColorPickerMode): ColorPickerBuilder {
        var list = modes.toList();
        if(modes.isEmpty()) {
            list = listOf(ColorPickerMode.HUE,ColorPickerMode.ALPHA,ColorPickerMode.BRIGHTNESS)
        }
        return ColorPickerBuilder(frame, title, value, list)
    }
}