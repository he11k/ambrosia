package rip.ambrosia.menu.creator

import com.google.gson.annotations.Expose

open class FrameHolder {
    @Expose
    val frames: MutableList<Frame> = ArrayList()

   open fun frame(title: String, key: String): FrameBuilder {
        val frameBuilder = FrameBuilder(title, key)
        frames.add(frameBuilder.frame)
        return frameBuilder
    }
}
