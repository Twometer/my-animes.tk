<template>
    <overlay>
        <backdrop :src="require('@/assets/wallpapers/profile.jpg')">
            <h1 class="login-header">Your Profile</h1>
            <h2 class="login-header">Tell us something about yourself</h2>

            <form class="login-form">
                <input type="text" placeholder="about me" />
                <input type="text" placeholder="avatar url" />
                <input type="text" placeholder="location" />
                <input type="text" placeholder="favorite character" />
                <input type="text" placeholder="favorite anime" />

                <button type="submit">Save</button>
            </form>
        </backdrop>
    </overlay>
</template>

<script>
import Backdrop from '../components/Backdrop.vue';
import Overlay from '../components/Overlay.vue';
export default {
    name: 'Profile',
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
