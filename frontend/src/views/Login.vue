<template>
    <overlay>
        <backdrop :src="require('@/assets/wallpapers/login.jpg')">
            <h1 class="login-header">myanimes</h1>
            <h2 class="login-header">Welcome back!</h2>
        </backdrop>
    </overlay>
</template>

<script>
import Backdrop from '../components/Backdrop.vue';
import Overlay from '../components/Overlay.vue';
export default {
    name: 'Login',
    components: {
        Overlay,
        Backdrop,
    },
};
</script>

<style scoped>
@import '../style/login.css';
</style>
