package rip.ambrosia.menu

import com.google.gson.annotations.Expose
import rip.ambrosia.menu.creator.Frame
import rip.ambrosia.menu.creator.FrameHolder

class Subcategory(val parent: Category,@Expose val title: String, val icon: String): FrameHolder() {
    fun getFrame(title: String): Frame? {
        for (frame in frames) {
            if(frame.title == title) {
                return frame
            }
        }
        return null
    }
}