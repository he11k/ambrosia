<script lang="ts">
    import { onMount } from "svelte";
    import lottie, { type AnimationItem } from "lottie-web";

    export let path: string;

    export let autoplay: boolean = true;

    export let loop: boolean = true;

    export let speed: number = 1;

    export let className: string = "";

    let containerElement: HTMLElement;

    let anim: AnimationItem;

    export function getAnimation(): AnimationItem {
        return anim;
    }

    export function play() {
        anim?.play();
    }

    export function stop() {
        anim?.stop();
    }

    export function setColor(color: string) {
        if (!anim) return;
        const paths = containerElement.querySelectorAll("path");
        paths.forEach((p) => p.setAttribute("fill", color));
    }
    onMount(() => {
        anim = lottie.loadAnimation({
            container: containerElement,
            renderer: "svg",
            loop: loop,
            autoplay: autoplay,
            path: path,
        });

        anim.setSpeed(speed);
        anim.addEventListener("DOMLoaded", () => {
            const paths = containerElement.querySelectorAll("path");
            paths.forEach((p) => p.setAttribute("fill", "currentColor"));
        });
    });
</script>

<div bind:this={containerElement} class={className}></div>
