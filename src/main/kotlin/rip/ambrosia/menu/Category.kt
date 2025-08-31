package rip.ambrosia.menu

import com.google.gson.annotations.Expose

open class Category(val parent: MainWindow,     @Expose val title: String, val icon: String)  {
    @Expose
    val subcategories: MutableList<Subcategory> = ArrayList<Subcategory>()
    fun createSubcategory(title: String, icon: String): Subcategory {
        val category = Subcategory(this, title, icon)
        subcategories.add(category)
        return category
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