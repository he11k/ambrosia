<script lang="ts">
  import { onMount } from "svelte";
  import Menu from "./lib/menu/Menu.svelte";
  import {
    selectedCategory,
    categories,
    selectedSubcategory,
  } from "./integration/global";
  import type { Subcategory, Category } from "./integration/types";
  console.log("Listener attached");
  onMount(() => {
    window.addEventListener("updateCategories", (event: any) => {

      const newCategories = event.detail as Category[];
      categories.set(newCategories);
      if (newCategories.length > 0) {
        selectedCategory.set(newCategories[0]);
        if (newCategories[0].subcategories.length > 0) {
          selectedSubcategory.set(newCategories[0].subcategories[0]);
        }
      }
    });
  });
</script>

<main>
  <div class="card">
    <Menu />
  </div>
</main>

<style>
</style>
