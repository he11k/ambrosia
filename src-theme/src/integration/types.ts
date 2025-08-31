export interface Category {
    subcategories: Subcategory[]
    name: string;
    icon: string;
}
export interface Subcategory {
    name: string;
    icon: string;
}