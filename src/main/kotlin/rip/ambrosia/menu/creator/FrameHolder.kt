package rip.ambrosia.menu.creator

import com.google.gson.annotations.Expose

open class FrameHolder {
    @Expose
    val frames: MutableList<Frame> = ArrayList()

   open fun frame(title: String): FrameBuilder {
        val frameBuilder = FrameBuilder(title)
        frames.add(frameBuilder.frame)
        return frameBuilder
    }
}
