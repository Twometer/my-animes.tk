<template>
    <overlay>
        <backdrop :src="require('@/assets/wallpapers/register.jpg')">
            <h1 class="login-header">myanimes</h1>
            <h2 class="login-header">Nice to meet you!</h2>
        </backdrop>
    </overlay>
</template>

<script>
import Backdrop from '../components/Backdrop.vue';
import Overlay from '../components/Overlay.vue';
export default {
    name: 'Register',
    components: {
        Overlay,
        Backdrop,
    },
};
</script>

<style scoped>
@import '../style/login.css';
</style>
