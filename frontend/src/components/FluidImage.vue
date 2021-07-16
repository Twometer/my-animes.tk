<template>
    <div
        class="fluid-image"
        :style="{
            'background-image': `url(${src})`,
        }"
    ></div>
</template>

<script>
export default {
    name: 'FluidImage',
    props: {
        src: String,
    },
};
</script>

<style scoped>
.fluid-image {
    background-position: center;
    background-color: #eeeeee;
    background-size: cover;
}
</style>
