package rip.ambrosia.module.movement

import net.minecraft.util.shape.VoxelShapes
import net.minecraftforge.eventbus.api.SubscribeEvent
import rip.ambrosia.Ambrosia
import rip.ambrosia.event.BlockShapeEvent
import rip.ambrosia.event.LevelTickEvent
import rip.ambrosia.event.PlayerJumpEvent
import rip.ambrosia.event.TickEvent
import rip.ambrosia.menu.creator.FrameBuilder
import rip.ambrosia.menu.creator.buttons.Checkbox
import rip.ambrosia.menu.creator.buttons.Selectbox
import rip.ambrosia.util.extensions.mc
import rip.ambrosia.util.extensions.player
import kotlin.text.compareTo

object AirJumpModule {
    val frame: FrameBuilder = Ambrosia.menu.primary.movement.player.frame("AirJump", "airjumpModule")
    val enable: Checkbox = frame.createCheckbox("Включить", false, "enable").build()
    val mode: Selectbox = frame.createSelectbox("Режим", "mode").addValue("JumpFreely").addSelectedValue("DoubleJump").addValue("GhostBlock").build()
    private var doubleJump = true
    val allowJump: Boolean
        get() = enable.value && (mode.isSelected("JumpFreely") || mode.isSelected("DoubleJump") && doubleJump)

    @SubscribeEvent
    fun onTick(event: LevelTickEvent) {
        if (player.isOnGround && mode.isSelected("DoubleJump")) {
            doubleJump = true
        }
    }
    @SubscribeEvent
    fun onBlockBox(event: BlockShapeEvent) {
        if (mode.isSelected("GhostBlock") && event.pos!!.y < player.blockPos.y && mc.options.jumpKey.isPressed) {
            event.shape = VoxelShapes.fullCube()
        }
    }

    @SubscribeEvent
    fun onPlayerJump(event: PlayerJumpEvent) {
        if (doubleJump && !player.isOnGround && mode.isSelected("DoubleJump")) {
            doubleJump = false
        }
    }

}