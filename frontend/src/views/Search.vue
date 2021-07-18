<template>
    <div class="row w-80 mt-5">
        <h1 class="mb-4">Search results for '{{ $route.query.q }}'</h1>
        <a
            v-for="result in results"
            :key="result._id"
            :href="'/anime/' + result._id"
        >
            <div class="search-result">
                <img width="100" :src="result.thumbnailUrl" />
                <div class="search-result-details">
                    <h1>{{ getTitle(result) }}</h1>
                    <h2>{{ typeToString(result.type) }}</h2>
                </div>
            </div>
        </a>
    </div>
</template>

<script>
import Api from '../services/api';
import Utils from '../services/utils';

export default {
    name: 'Search',
    data: () => {
        return {
            results: [],
        };
    },
    components: {},
    async mounted() {
        let query = this.$route.query.q;
        if (query == null || query == '') return;

        let results = await Api.Search.search(query);
        this.results = results;
        console.log(results);
    },
    methods: {
        typeToString(type) {
            return Utils.typeToString(type);
        },
        getTitle(anime) {
            return Utils.getTitle(anime, ['en', 'en_us', 'en_jp', 'ja_jp']);
        },
    },
};
</script>

<style scoped>
a {
    color: black;
    text-decoration: none;
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
    font-size: 2rem;
    margin-bottom: 1rem;
}
.search-result-details > h2 {
    font-weight: 300;
    font-size: 1.1rem;
}
</style>