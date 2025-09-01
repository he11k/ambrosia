package rip.ambrosia.menu


import com.google.gson.annotations.Expose
import java.util.*

class MainWindow {
    class MainCategory(val window: MainWindow) : Category(window, "Основное", "house", "mainCategory") {
        val attackAura: Subcategory = createSubcategory("Аура", "sword", "auraSubcategory")
        val legit: Subcategory = createSubcategory("Легит", "sword", "legitSubcategory")
        val action = createSubcategory("Действия", "sword", "actionSubcategory")
        val handle = createSubcategory("Аукцион", "sword", "handleSubcategory")
    }
    class MovementCategory(val window: MainWindow) : Category(window, "Движение", "person", "movementCategory") {

    }
    class VisualCategory(val window: MainWindow) : Category(window, "Визуалы", "visual", "visualCategory") {
        val display: Subcategory = createSubcategory("Оверлей", "sword", "displaySubcategory")
        val world: Subcategory = createSubcategory("Мир", "sword", "worldSubcategory")
        val entities: Subcategory = createSubcategory("Ентити", "sword", "entitiesSubcategory")
    }

    @Expose
    var categories: EnumMap<Category.Group, MutableList<Category>> = EnumMap(Category.Group::class.java)
    val main: MainCategory = createCategory(MainCategory(this), Category.Group.CATEGORIES)
    val movement: MovementCategory = createCategory(MovementCategory(this), Category.Group.CATEGORIES)
    val visual: VisualCategory = createCategory(VisualCategory(this), Category.Group.CATEGORIES)
    val environment: Category = createCategory(Category(this, "Окружение", "pickaxe", "environmentCategory"), Category.Group.CATEGORIES)
    val information: Category = createCategory(Category(this, "Информация", "loupe", "informationCategory"), Category.Group.CATEGORIES)
    val configs: Category = createCategory(Category(this, "Конфиги", "gear", "configCategory"), Category.Group.OTHER)

    constructor() {

    }

    fun getCategoryFromKey(key: String): Category? {
        for (category in categories[Category.Group.CATEGORIES]!!) {
            if (category.key == key) {
                return category
            }
        }
        return null
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