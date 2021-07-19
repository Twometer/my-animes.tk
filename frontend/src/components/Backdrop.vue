<template>
    <fluid-image class="backdrop-image" :src="src" />
    <div class="backdrop-overlay">
        <div class="overlay-container">
            <div class="overlay-content">
                <slot></slot>
            </div>
        </div>
    </div>
</template>

<style scoped>
.backdrop-image {
    position: absolute;
    width: 100vw;
    height: 100vh;
    z-index: 1150;
}
.backdrop-overlay {
    position: absolute;
    width: 100vw;
    height: 100vh;
    z-index: 1160;
    box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.3);
    background: rgba(255, 255, 255, 0.7);
    backdrop-filter: blur(32px);
}
@media (min-width: 576px) {
    .backdrop-overlay {
        width: 60vw;
        background: rgba(255, 255, 255, 0.9);
    }
}

@media (min-width: 992px) {
    .backdrop-overlay {
        width: 50vw;
    }
}

@media (min-width: 1600px) {
    .backdrop-overlay {
        width: 40vw;
    }
}

.overlay-container {
    height: 100%;
    display: flex;
    flex-flow: row;
    align-items: center;
}

.overlay-content {
    padding: 12%;
}
</style>

<script>
import FluidImage from './FluidImage.vue';
export default {
    name: 'Backdrop',
    components: {
        FluidImage,
    },
    props: {
        src: String,
    },
};
</script>
