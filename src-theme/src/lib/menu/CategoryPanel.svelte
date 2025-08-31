<script lang="ts">
    import CategoryComponent from "./category/CategoryComponent.svelte";
    import type { Category, Subcategory } from "../../integration/types";
    import { get } from 'svelte/store';
    import  {
        selectedCategory,
        categories,
        setSelectedCategory,
    } from "../../integration/global";

    function handleCategorySelect(event: CustomEvent<Category>) {
        setSelectedCategory(event.detail);
    }
</script>

<div>
    <div class="logo">
        <img src="/public/logo.svg" alt="" />
        Ambrosia
    </div>
    <div class="search-block">
        <input class="search" placeholder="Поиск..." />
        <img
            src="/public/search.svg"
            alt=""
            style="display:flex; position: relative; left: -129px; top: 0px; pointer-events: none;"
        />
    </div>

    <div class="scrollable-area">
        <div class="groupc">Категории</div>
        {#each get(categories) as category (category.name)}
            <CategoryComponent
                on:select={handleCategorySelect}
                {category}
                selected={$selectedCategory.name === category.name}
            />
        {/each}
    </div>
</div>

<style>
    .scrollable-area {
        max-height: 290px;
        overflow-y: auto;
        overflow-x: hidden;
        scrollbar-width: none;
        mask-image: linear-gradient(
            to bottom,
            transparent 0%,
            black 10px,
            black calc(100% - 10px),
            transparent 100%
        );
    }
    .groupc {
        display: flex;
        align-items: center;
        font-size: 12px;
        margin-left: 11px;
        margin-top: 6px;
        margin-bottom: 3px;
        color: #8e8e93;
    }

    .logo {
        color: #ffffff;
        display: flex;
        align-items: center;
        gap: 3px;
        margin-left: 11px;
        margin-top: 6px;
        font-size: 13px;
        letter-spacing: 0.5px;
    }
    .search-block {
        display: flex;
        padding: 0px;
        margin-top: 9px;
        margin-left: 9px;
    }
    .search {
        padding: 0px;
        max-width: calc(128px - 15px);
        max-height: 22px;

        background-color: rgba(255, 255, 255, 0.04);
        border-radius: 6px;
        outline: 1px solid #27272a;
        border-color: #ffffff00;
        color: #8e8e93;
        padding-left: 15px;
    }
</style>
