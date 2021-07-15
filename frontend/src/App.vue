<template>
    <nav id="nav" class="navbar navbar-expand-md navbar-dark">
        <div class="container-fluid justify-content-between w-80">
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

            <div class="collapse navbar-collapse justify-content-evenly" id="navbarContent">
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
    --text-color: #707070;
}

#app {
    font-family: 'Open Sans', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: var(--text-color);
    font-weight: 100;
}

#nav {
    background: var(--primary-color);
    box-shadow: 0px -20px 8px 20px rgba(0, 0, 0, 0.35);
    padding: 0.4rem 0;
}

.navbar-brand {
    font-family: 'Unica One';
    font-size: 1.8rem;
    padding: 0;
    padding-top: 2px;
}

.w-80 {
    width: 80%;
    margin: auto;
}

.raw-text {
    white-space: pre-wrap;
}

::selection {
    background: #dedeff;
}

.no-scrollbar {
    overflow-y: hidden;
}
</style>

<script>
import NavbarSearch from './components/NavbarSearch.vue';
export default {
    components: {
        NavbarSearch,
    },
};
</script>