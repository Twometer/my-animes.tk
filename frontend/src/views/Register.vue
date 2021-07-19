<template>
    <overlay>
        <backdrop :src="require('@/assets/wallpapers/register.jpg')">
            <h1 class="login-header">myanimes</h1>
            <h2 class="login-header">Nice to meet you!</h2>

            <form class="login-form">
                <input type="text" placeholder="username" />
                <input type="password" placeholder="password" />
                <input type="password" placeholder="confirm password" />

                <button type="submit">Register</button>
                <router-link to="login"> already have an account? </router-link>
            </form>
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
@import '../style/button.css';
</style>
