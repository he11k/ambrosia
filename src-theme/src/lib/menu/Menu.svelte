<script lang="ts">
    import CategoryPanel from "./CategoryPanel.svelte";

    let x = $state(100);
    let y = $state(100);
    let tempX = $state(0);
    let tempY = $state(0);
    let moves = $state(false);
    const bound = { width: 665, height: 396 };
    let windowWidth = 0;
    let windowHeight = 0;
    function clamp(value: number, min: number, max: number): number {
        return Math.min(Math.max(value, min), max);
    }
    function handleMouseDown(e: MouseEvent) {
        const rect = (e.target as HTMLElement).getBoundingClientRect();
        const cursor = getComputedStyle(e.target as HTMLElement).cursor;
        if (cursor !== "default") return;

        if (
            e.clientX >= rect.left &&
            e.clientX <= rect.right &&
            e.clientY >= rect.top &&
            e.clientY <= rect.bottom
        ) {
            if (e.button === 0) {
                moves = true;
                tempX = x - e.clientX;
                tempY = y - e.clientY;
            }
        }
    }
    function handleMouseUp(e: MouseEvent) {
        if (e.button === 0) moves = false;
    }
    function handleMouseMove(e: MouseEvent) {
        if (moves) {
            requestAnimationFrame(() => {
                x = clamp(e.clientX + tempX, 0, windowWidth - bound.width);
                y = clamp(e.clientY + tempY, 0, windowHeight - bound.height);
            });
        }
    }
    function updateWindowSize() {
        windowWidth = window.innerWidth;
        windowHeight = window.innerHeight;
    }

    updateWindowSize();
    window.addEventListener("resize", updateWindowSize);
</script>

<svelte:window on:mousemove={handleMouseMove} on:mouseup={handleMouseUp} />

<div class="no-select">
    <div
        role="button"
        tabindex="0"
        class="box"
        style:left={x + "px"}
        style:top={y + "px"}
        style:width={bound.width + "px"}
        style:height={bound.height + "px"}
        onmousedown={handleMouseDown}
    >
        <div class="layout" style:height={bound.height + "px"}>
            <aside class="sidebar"><CategoryPanel /></aside>
            <header class="header">Хедер</header>
            <main class="content">Контент</main>
        </div>
    </div>
</div>

<style>
    .no-select {
        user-select: none;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        cursor: default;
    }
    .box {
        position: absolute;
        background: #141414c4;
        outline: 1px solid #27272a;
        border-radius: 8px;
        backdrop-filter: blur(10px); /* TEMP */
    }
    .layout {
        display: grid;
        grid-template-areas:
            "sidebar header"
            "sidebar content"
            "sidebar footer";
        grid-template-columns: 151px 1fr;
        grid-template-rows: 38px 1fr;
    }

    .sidebar {
        grid-area: sidebar;
        border-right: 1px solid rgba(255, 255, 255, 0.1);
        /* background: #111; */
    }
    .header {
        grid-area: header;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        /* background: #222; */
    }
    .content {
        grid-area: content;
        /* background: #1b1b1b; */
    }
</style>
