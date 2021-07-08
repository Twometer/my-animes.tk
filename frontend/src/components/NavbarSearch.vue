<template>
    <div class="nav-search">
        <icon name="search" size=20 />
        <input type="text" placeholder="Search..." />
    </div>
</template>

<script>
import Icon from './Icon.vue';
export default {
    name: 'NavbarSearch',
    components: {
        Icon,
    },
};
</script>

<style scoped>
.nav-search {
    margin: 0.5rem 1rem;
    padding: 0.2rem 0.5rem;
    border-radius: 4px;
    background-color: rgba(0, 0, 0, 0.2);
}
.nav-search > input {
    border: 0;
    outline: 0;
    color: white;
    width: 350px;
    background-color: transparent;
    font-size: 0.9rem;
    margin-left: 1rem;
}
.nav-search > img {
    margin-left: 0.25rem;
}
::placeholder {
    color: rgba(255, 255, 255, 0.5);
}
</style>