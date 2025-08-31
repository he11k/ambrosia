<script lang="ts">
    import { createEventDispatcher } from "svelte";
    import type { Subcategory, Category } from "../../../integration/types";
    import SubcategoryComponent from "./SubcategoryComponent.svelte";
    import LottieIcon from "../../util/LottieIcon.svelte";
    import {
        selectedSubcategory,
        categories,
        setSelectedSubcategory,
    } from "../../../integration/global";
    export let category: Category;
    export let selected: boolean = false;

    let lottieIcon: LottieIcon;
    type CategoryEvents = {
        select: Category;
    };
    const dispatch = createEventDispatcher<CategoryEvents>();
    function handleClick() {
        dispatch("select", category);
    }

    function handleSubcategorySelect(event: CustomEvent<Subcategory>) {
        setSelectedSubcategory(event.detail);
    }
</script>

<div class="category-block">
    <div
        role="button"
        tabindex="0"
        class="category"
        class:selected
        onmousedown={() => {
            lottieIcon.stop();
            lottieIcon.play();
            handleClick();
        }}
    >
        <div class="icon-conditioner" class:selected>
            <LottieIcon
                bind:this={lottieIcon}
                path="/shoping.json"
                speed={1.2}
                loop={false}
                autoplay={false}
                className="category-icon"
            />
        </div>
        <div class="text" class:selected>
            {category.name}
        </div>
    </div>
    <div
        class="select-animation"
        class:selected
        style="--translate-y: {category.subcategories.length * 26}px;"
    >
        {#each category.subcategories as subcategory (subcategory.name)}
            <SubcategoryComponent
                on:select={handleSubcategorySelect}
                {subcategory}
                selected={$selectedSubcategory.name === subcategory.name}
            />
        {/each}
    </div>
</div>

<style>
    .select-animation {
        max-height: 0;
        opacity: 0;
        overflow: hidden;
        transition:
            max-height 0.35s cubic-bezier(0.65, 0, 0.35, 1),
            opacity 0.35s cubic-bezier(0.65, 0, 0.35, 1);
    }
    .select-animation.selected {
        max-height: var(--translate-y);
        opacity: 1;
    }
    .icon-conditioner {
        transition:
            color 0.35s ease-out,
            padding-left 0.25s ease-out;
        width: 16px;
        padding-top: 4px;
        padding-left: 8px;
    }
    .icon-conditioner.selected {
        padding-left: 4px;
        color: #d5b4ff;
    }
    .category-block {
        padding-bottom: 4px;
    }
    .category {
        display: flex;
        align-items: center;
        font-size: 12px;
        width: 135px;
        height: 24px;
        margin-left: 8px;
        padding: 0;
        border-radius: 6px;
        cursor: pointer;
        transition: background-color 0.35s ease-out;
    }
    .category.selected {
        background-color: rgba(255, 255, 255, 0.1);
    }
    .category.selected:hover {
        background-color: rgba(255, 255, 255, 0.13);
        transition: background-color 0.25s ease-out;
    }
    .category:hover {
        transition: background-color 0.25s ease-out;
        background-color: rgba(255, 255, 255, 0.03);
    }

    .text {
        margin-left: 3px;
        color: #e5e5ea;
        transition: color 0.35s ease-out;
    }
    .text.selected {
        color: #ffffff;
    }
</style>
