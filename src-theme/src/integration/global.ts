import type { Subcategory, Category } from "./types";
import { writable } from 'svelte/store';
import { get } from 'svelte/store';

export const categories = writable<Category[]>([
    {
        subcategories: [
            { name: "Аура", icon: "" },
            { name: "Легит", icon: "" },
            { name: "Действия", icon: "" },
        ],
        name: "Основное",
        icon: "",
    },
    {
        subcategories: [
            { name: "Залупа", icon: "" },
            { name: "Пидорасы", icon: "" },
            { name: "Долбаебы", icon: "" },
        ],
        name: "Движение",
        icon: "",
    },
    {
        subcategories: [{ name: "Писька", icon: "" }],
        name: "Визуалы",
        icon: "",
    },
]);

export const selectedCategory = writable<Category>(get(categories)[0]);
export const selectedSubcategory = writable<Subcategory>(get(categories)[0].subcategories[0]);

export function setSelectedCategory(cat: Category) {
    selectedCategory.set(cat);
}

export function setSelectedSubcategory(cat: Subcategory) {
    selectedSubcategory.set(cat);
}