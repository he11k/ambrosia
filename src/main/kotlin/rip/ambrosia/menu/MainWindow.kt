package rip.ambrosia.menu


import com.google.gson.annotations.Expose
import java.util.*

class MainWindow {
    class MainCategory(val window: MainWindow) : Category(window, "Основное", "house") {
        val attackAura: Subcategory = createSubcategory("Аура", "sword")
        val blatant: Subcategory = createSubcategory("Легит", "sword")
        val legit = createSubcategory("Действия", "sword")
        val handle = createSubcategory("Аукцион", "sword")
    }
    class MovementCategory(val window: MainWindow) : Category(window, "Движение", "person") {

    }
    class VisualCategory(val window: MainWindow) : Category(window, "Визуалы", "visual") {
        val display: Subcategory = createSubcategory("Оверлей", "sword")
        val world: Subcategory = createSubcategory("Мир", "sword")
        val entities: Subcategory = createSubcategory("Ентити", "sword")
    }

    @Expose
    var categories: EnumMap<Category.Group, MutableList<Category>> = EnumMap(Category.Group::class.java)
    val main: MainCategory = createCategory(MainCategory(this), Category.Group.CATEGORIES)
    val movement: MovementCategory = createCategory(MovementCategory(this), Category.Group.CATEGORIES)
    val visual: VisualCategory = createCategory(VisualCategory(this), Category.Group.CATEGORIES)
    val environment: Category = createCategory(Category(this, "Окружение", "pickaxe"), Category.Group.CATEGORIES)
    val information: Category = createCategory(Category(this, "Информация", "loupe"), Category.Group.CATEGORIES)
    val configs: Category = createCategory(Category(this, "Конфиги", "gear"), Category.Group.OTHER)

    constructor() {

    }

    fun <T> createCategory(category: T, group: Category.Group): T {
        if (categories[group] == null) {
            for (value in Category.Group.entries) {
                categories[value] = ArrayList<Category>()
            }
        }
        categories[group]!!.add(category as Category)
        return category
    }

    fun getCategory(name: String): Category? {
        for (category in categories[Category.Group.CATEGORIES]!!) {
            if (category.title == name) {
                return category
            }
        }
        return null
    }
}