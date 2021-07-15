import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
    },
    {
        path: '/login',
        name: 'Log in',
        component: () => import('../views/Login.vue'),
    },
    {
        path: '/anime/:animeId',
        name: 'Anime',
        component: () => import('../views/Anime.vue'),
    },
    {
        path: '/user/:username',
        name: 'User',
        component: () => import('../views/User.vue'),
    },
    {
        path: '/profile',
        name: 'Profile',
        component: () => import('../views/Profile.vue'),
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue'),
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue'),
    },
    {
        path: '/search',
        name: 'Search',
        component: () => import('../views/Search.vue'),
    },
    {
        path: '/:catchAll(.*)',
        name: 'Not found',
        component: () => import('../views/NotFound.vue'),
        props: route => ({ query: route.query.q })
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;
