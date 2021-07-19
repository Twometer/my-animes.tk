<template>
    <div class="row w-80 mt-5">
        <h1 class="mb-4">Search results for '{{ $route.query.q }}'</h1>
        <loader class="m-5" v-if="loading" />
        <div v-if="!loading">
            <router-link
                v-for="result in results"
                :key="result._id"
                :to="'/anime/' + result._id"
            >
                <div class="search-result">
                    <img width="100" :src="result.thumbnailUrl" />
                    <div class="search-result-details">
                        <h1>{{ getTitle(result) }}</h1>
                        <h2>{{ typeToString(result.type) }}</h2>
                    </div>
                </div>
            </router-link>
        </div>
    </div>
</template>

<script>
import Loader from '../components/Loader.vue';
import Api from '../services/api';
import Utils from '../services/utils';

export default {
    name: 'Search',
    data: () => {
        return {
            results: [],
            loading: true,
        };
    },
    components: { Loader },
    mounted() {
        this.reload();
    },
    watch: {
        '$route.params': function () {
            this.reload();
        },
    },
    methods: {
        typeToString(type) {
            return Utils.typeToString(type);
        },
        getTitle(anime) {
            return Utils.getTitle(anime, ['en', 'en_us', 'en_jp', 'ja_jp']);
        },
        async reload() {
            this.loading = true;

            let query = this.$route.query.q;
            if (query == null || query == '') return;

            let results = await Api.Search.search(query);
            this.results = results;
            console.log(results);

            this.loading = false;
        },
    },
};
</script>

<style scoped>
a {
    color: black;
    text-decoration: none;
}
h1 {
    font-size: 2rem;
}
.search-result {
    display: flex;
    flex-flow: row;
    align-items: center;
    padding: 0.75rem;
    margin-top: 0.75rem;
    margin-bottom: 0.5rem;
    margin-left: 3rem;
    margin-right: 3rem;
    transition-duration: 0.3s;
}
.search-result:hover {
    background-color: #f9f9f9;
    border-radius: 4px;
    transition-duration: 0.3s;
}
.search-result > img {
    border-radius: 3px;
}
.search-result-details {
    margin-left: 3rem;
}
.search-result-details > h1 {
    color: var(--primary-color);
    font-weight: 500;
    font-size: 1.75rem;
    margin-bottom: 1rem;
}
.search-result-details > h2 {
    font-weight: 300;
    font-size: 1.1rem;
}
</style>