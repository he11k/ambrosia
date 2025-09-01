package rip.ambrosia.menu

import com.google.gson.annotations.Expose

open class Category(val parent: MainWindow, @Expose val title: String, val icon: String, val key: String)  {
    @Expose
    val subcategories: MutableList<Subcategory> = ArrayList<Subcategory>()
    fun createSubcategory(title: String, icon: String, key: String): Subcategory {
        val category = Subcategory(this, title, icon, key)
        subcategories.add(category)
        return category
    }
    fun getCategoryFromKey(key: String): Subcategory? {
        for (category in subcategories) {
            if (category.key == key) {
                return category
            }
        }
        return null
    }

    fun getCategory(name: String): Subcategory? {
        for (category in subcategories) {
            if(category.title == name) {
                return category
            }
        }
        return null
    }
    enum class Group(val index: Int) {
        CATEGORIES(0), OTHER(1);
    }
}