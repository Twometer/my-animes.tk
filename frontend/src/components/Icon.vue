<template>
    <img :height="size" :src="require(`../assets/icons/${name}.svg`)" />
</template>

<script>
export default {
    name: 'Icon',
    props: {
        name: String,
        size: Number,
    },
};
</script>

<style scoped>
img {
    margin-top: -1px;
}
</style>
