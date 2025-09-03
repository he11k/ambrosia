package rip.ambrosia.menu.creator

import rip.ambrosia.menu.creator.buttons.*

class FrameBuilder(title: String, contentKey: String, frameHolder: FrameHolder) {
    val frame: Frame = Frame(title, contentKey, frameHolder)

    fun createCheckbox(title: String, value: Boolean, contentKey: String): CheckboxBuilder {
        return CheckboxBuilder(frame, title, value, contentKey)
    }
    fun createSelectbox(title: String, contentKey: String): SelectboxBuilder {
        return SelectboxBuilder(frame, title,  contentKey)
    }

    fun createSlider(
        title: String,
        value: Float,
        minimum: Float,
        maximum: Float,
        increment: Float, contentKey: String
    ): SliderBuilder {
        return SliderBuilder(frame, title, value, minimum, maximum, increment,contentKey)
    }

    fun createMultiSlider(
        title: String,
        minimumValue: Float,
        maximumValue: Float,
        minimum: Float,
        maximum: Float,
        increment: Float, contentKey: String
    ): MultiSliderBuilder {
        return MultiSliderBuilder(frame, title, minimumValue, maximumValue, minimum, maximum, increment,contentKey)
    }

    fun createColorPicker(title: String, value: Int, vararg modes: ColorPickerMode, contentKey: String): ColorPickerBuilder {
        var list = modes.toList();
        if(modes.isEmpty()) {
            list = listOf(ColorPickerMode.HUE,ColorPickerMode.ALPHA,ColorPickerMode.BRIGHTNESS)
        }
        return ColorPickerBuilder(frame, title, value, list,contentKey)
    }
}