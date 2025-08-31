package rip.ambrosia.menu.creator.buttons

import rip.ambrosia.menu.creator.Button
import rip.ambrosia.menu.creator.ButtonBuilder
import rip.ambrosia.menu.creator.ButtonType
import rip.ambrosia.menu.creator.Frame
import java.awt.Color


class ColorPicker(
    frame: Frame,
    icon: String,
    title: String,
    description: String,
    value: Int,
   val colorPickerModes: List<ColorPickerMode>
) : Button<Int>(frame, icon, title, description, ButtonType.COLORPICKER, value) {
    var hue = 0.0f
    var saturation = 0.0f
    var brightness = 0.0f
    var alpha = 0.0f

    init {
        val hsb = colorToHSB(value)
        hue = hsb.first
        saturation = hsb.second
        brightness = hsb.third
        alpha = ((value shr 24) and 0xFF) / 255F
    }

    private fun colorToHSB(color: Int): Triple<Float, Float, Float> {
        val r = (color shr 16 and 0xFF) / 255f
        val g = (color shr 8 and 0xFF) / 255f
        val b = (color and 0xFF) / 255f

        val max = maxOf(r, g, b)
        val min = minOf(r, g, b)
        val delta = max - min

        val hueDeg = when {
            delta == 0f -> 0f
            max == r -> ((g - b) / delta) % 6f
            max == g -> ((b - r) / delta) + 2f
            else -> ((r - g) / delta) + 4f
        } * 60f

        val hue = ((if (hueDeg < 0f) hueDeg + 360f else hueDeg) / 360f)
        val saturation = if (max == 0f) 0f else delta / max
        val brightness = max

        return Triple(hue, saturation, brightness)
    }

    fun getColor(): Int {
        val color = Color.HSBtoRGB(hue, saturation, brightness)
        val alpha = (alpha * 255F).toInt()
        val red = color shr 16 and 0xFF
        val green = color shr 8 and 0xFF
        val blue = color and 0xFF
        return alpha shl 24 or (red shl 16) or (green shl 8) or blue
    }

    fun isOnlySelected(selected: ColorPickerMode): Boolean {
        for (mode in colorPickerModes) {
            if (selected == mode && colorPickerModes.size == 1) {
                return true
            }
        }
        return false
    }

}

class ColorPickerBuilder(
    frame: Frame,
    title: String,
    value: Int,
    val colorPickerModes: List<ColorPickerMode>
) : ButtonBuilder<ColorPicker, Int, ColorPickerBuilder>(frame, title, value) {
    override fun build(): ColorPicker {
        var list = colorPickerModes
        if (colorPickerModes.size == 1 && colorPickerModes[0] == ColorPickerMode.BRIGHTNESS) {
            list = listOf(ColorPickerMode.HUE, ColorPickerMode.BRIGHTNESS)
        }
        val slider = ColorPicker(frame, icon, title, description, value, colorPickerModes)
        slider.conditions(activeConditions, renderConditions)
        frame.addButton(slider)
        return slider
    }
}

enum class ColorPickerMode {
    HUE, ALPHA, BRIGHTNESS
}
