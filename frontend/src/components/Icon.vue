<template>
    <img
        :height="size"
        :style="{ color: 'white' }"
        src="@/assets/icons/search.svg"
    />
</template>

<script>
export default {
    name: 'Icon',
    props: {
        name: String,
        size: Number
    },
};
</script>

<style scoped>
img {
    margin-top: -1px;
}
</style>