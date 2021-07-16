<template>
    <overlay class="homepage-overlay">
        <div class="content-box">
            <h1>myanimes</h1>
            <h2>Where you can track and share the animes you love :)</h2>

            <form class="search" v-on:submit.prevent="submit">
                <icon name="search" size="20" />
                <input type="text" placeholder="Search..." v-model="query" />
            </form>

            <div class="button-bar">
                <router-link to="login">
                    <button>Log in</button>
                </router-link>
                <router-link to="register">
                    <button>Join</button>
                </router-link>
            </div>
        </div>
    </overlay>
</template>

<script>
import Icon from '../components/Icon.vue';
import Overlay from '../components/Overlay.vue';

export default {
    name: 'Home',
    components: {
        Overlay,
        Icon,
    },
    data() {
        return {
            query: '',
        };
    },
    methods: {
        submit() {
            this.$router.push({
                name: 'Search',
                query: { q: this.query },
            });
        },
    },
};
</script>

<style scoped>
@import '../style/button.css';

.homepage-overlay {
    display: flex;
    align-items: center;
    justify-content: center;
    background-image: url('~@/assets/wallpapers/index.jpg');
    background-position: center;
    background-size: cover;
}
.content-box {
    color: white;
    backdrop-filter: blur(32px);
    text-align: center;
    padding: 3rem;
    border-radius: 5px;
}
h1 {
    font-family: 'Unica One';
    font-size: 2.5rem;
    margin-bottom: 2rem;
}
h2 {
    font-weight: 100;
    font-size: 1.5rem;
}

.search {
    display: inline-block;
    margin: 5rem 0;
    padding: 0.4rem 0.5rem;
    border-radius: 4px;
    background-color: rgba(0, 0, 0, 0.2);
}
.search > input {
    border: 0;
    outline: 0;
    color: white;
    background-color: transparent;
    font-size: 0.9rem;
    margin-left: 1rem;
    width: 300px;
}
.search > img {
    margin-left: 0.25rem;
}
::placeholder {
    color: rgba(255, 255, 255, 0.5);
}
::selection {
    background-color: rgba(255, 255, 255, 0.5);
}

button {
    margin-left: 1rem;
    margin-right: 1rem;
}
</style>
