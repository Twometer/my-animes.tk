<template>
    <nav id="nav" class="navbar navbar-expand-md navbar-dark">
        <div class="container-fluid">
            <router-link class="navbar-brand" to="/">myanimes</router-link>

            <button
                class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarContent"
                aria-controls="navbarContent"
                aria-expanded="false"
                aria-label="Toggle navigation"
            >
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarContent">
                <navbar-search />

                <ul class="navbar-nav">
                    <li class="nav-item">
                        <router-link class="nav-link" to="/login">
                            Log in
                        </router-link>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <router-view />
</template>

<style>
:root {
    --primary-color: #876fff;
}

#app {
    font-family: 'Open Sans', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
}

#nav {
    background: var(--primary-color);
    box-shadow: 0px 0px 12px 1px rgba(0, 0, 0, 0.3);
    padding: 0;
}

#nav > div {
    width: 80%;
}

.navbar-brand {
    font-family: 'Unica One';
    font-size: 1.8rem;
    margin-top: 0.5rem;
    margin-bottom: 0.4rem;
}
</style>

<script>
import NavbarSearch from './components/NavbarSearch.vue';
export default {
    components: {
        NavbarSearch,
    },
};
</script>NavbarSearch