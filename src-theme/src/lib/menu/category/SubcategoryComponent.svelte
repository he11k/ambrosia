<script lang="ts">
    import { createEventDispatcher } from "svelte";
    import type { Subcategory, Category } from "../../../integration/types";
    import LottieIcon from "../../util/LottieIcon.svelte";
    export let subcategory: Subcategory;
    export let selected: boolean = false;
    let lottieIcon: LottieIcon;

    type SubcategoryEvents = {
        select: Subcategory;
    };
    const dispatch = createEventDispatcher<SubcategoryEvents>();
    function handleClick() {
        dispatch("select", subcategory);
    }
</script>

<div class="subcategory-block">
    <div
        role="button"
        tabindex="0"
        class="subcategory"
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
            {subcategory.name}
        </div>
    </div>
</div>

<style>
    .icon-conditioner {
        color: #aeaeb2;
        transition: color 0.25s ease-out;
        width: 16px;
        padding-top: 4px;
        padding-left: 4px;
    }
    .icon-conditioner.selected {
        color: #ffffff;
    }
    .subcategory {
        display: flex;
        align-items: center;
        font-size: 12px;
        width: 125px;
        height: 22px;

        margin-left: 18px;
        margin-top: 2px;
        padding: 0;
        border-radius: 6px;
        cursor: pointer;

        transition: background-color 0.25s ease-out, margin-left 0.15s ease-out;
    }
    .subcategory.selected {
        background-color: rgba(255, 255, 255, 0.03);
        margin-left: 16px;
    }
    .subcategory.selected:hover {
        background-color: rgba(255, 255, 255, 0.05);
        transition: background-color 0.25s ease-out, margin-left 0.15s ease-out;
    }
    .subcategory:hover {
        transition: background-color 0.25s ease-out, margin-left 0.15s ease-out;
        background-color: rgba(255, 255, 255, 0.02);
    }
    .text {
        margin-left: 3px;
        color: #aeaeb2;
        transition: color 0.25s ease-out;
    }
    .text.selected {
        color: #ffffff;
    }
</style>
