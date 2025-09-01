package rip.ambrosia.menu

import com.google.gson.annotations.Expose
import rip.ambrosia.menu.creator.Frame
import rip.ambrosia.menu.creator.FrameHolder

class Subcategory(val parent: Category,@Expose val title: String, val icon: String, val key: String): FrameHolder() {
    fun getFrame(title: String): Frame? {
        for (frame in frames) {
            if(frame.title == title) {
                return frame
            }
        }
        return null
    }
    fun getFrameFromKey(key: String): Frame? {
        for (frame in frames) {
            if (frame.contentKey == key) {
                return frame
            }
        }
        return null
    }

}