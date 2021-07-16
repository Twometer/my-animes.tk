<template>
    <div v-if="!loading">
        <section
            class="anime-banner"
            :style="{
                'background-image': `url('${anime.coverImageUrl}')`,
            }"
        ></section>
        <section class="anime-overview">
            <section class="anime-header w-80">
                <div
                    class="anime-poster"
                    :style="{
                        'background-image': `url('${anime.posterUrl}')`,
                    }"
                />
                <div class="anime-meta w-80">
                    <section>
                        <h1>{{ englishTitle }}</h1>
                        <h2>{{ japaneseTitle }}</h2>
                    </section>

                    <button>Add to list</button>
                </div>
            </section>
            <section class="anime-synopsis w-80 raw-text">
                {{ anime.synopsis }}
            </section>
        </section>
        <section class="anime-details">
            <div class="row w-80">
                <div class="col">
                    <div v-if="anime.type !== 'movie'">
                        <h3>Episodes</h3>
                        <div
                            class="anime-block anime-episode"
                            v-for="episode in anime.episodes"
                            :data-no="episode.episodeNo"
                            :key="episode.episodeNo"
                            :style="{
                                'background-image': `url('${
                                    episode.thumbnailUrl == null
                                        ? anime.coverImageUrl
                                        : episode.thumbnailUrl
                                }')`,
                            }"
                        >
                            <div class="anime-block-title">
                                {{ episode.title }}
                            </div>
                        </div>
                    </div>

                    <h3 class="mt-5">Characters</h3>
                    <div
                        class="anime-block anime-character"
                        v-for="character in anime.characters"
                        :data-name="character.name"
                        :key="character.name"
                        :style="{
                            'background-image': `url('${
                                character.pictureUrl == null
                                    ? anime.coverImageUrl
                                    : character.pictureUrl
                            }')`,
                        }"
                    >
                        <div class="anime-block-title">
                            {{ character.name }}
                        </div>
                    </div>
                </div>
                <div class="col-auto">
                    <div class="details-pane">
                        <h4 class="mt-0">Romaji Title</h4>
                        {{ romajiTitle }}

                        <h4>Status</h4>
                        {{ status }}

                        <h4>Type</h4>
                        {{ type }}

                        <h4>Studios</h4>
                        {{ anime.studios.slice(0, 3).join(', ') }}

                        <h4>Airing</h4>
                        {{ airingDuration }}

                        <h4>Genres</h4>
                        {{ anime.genres.join(', ') }}

                        <h4>Episodes</h4>
                        {{ anime.episodes.length }}

                        <h4>Duration</h4>
                        {{ totalDuration }} ({{ anime.episodeLength }} min per
                        episode)

                        <h4>Age rating</h4>
                        {{ anime.ageRating }}

                        <h4>NSFW</h4>
                        {{ anime.nsfw ? 'Yes' : 'No' }}
                    </div>
                    <div
                        class="anime-trailer"
                        v-if="anime.trailerYoutubeId != null"
                        :style="{
                            'background-image': `url('https://i.ytimg.com/vi/${anime.trailerYoutubeId}/hqdefault.jpg')`,
                        }"
                    >
                        <a
                            :href="`https://youtu.be/${anime.trailerYoutubeId}`"
                            class="anime-trailer-link"
                            target="_blank"
                        >
                            <icon name="youtube" size="30" />&nbsp;&nbsp;Watch
                            trailer
                        </a>
                    </div>
                </div>
            </div>
        </section>

        <!-- Character information dialog -->
        <modal :open="selectedCharacter != null" class="text-center">
            <img :src="selectedCharacter.pictureUrl" class="mb-3" />
            <h1>{{ selectedCharacter.name }}</h1>
            <p class="raw-text">{{ selectedCharacter.description }}</p>
            <button v-on:click="selectedCharacter = null" class="m-auto">
                Close
            </button>
        </modal>

        <!-- Episode information dialog -->
        <modal :open="selectedEpisode != null" :borderless="true">
            <fluid-image
                class="episode-detail-thumbnail"
                :src="selectedEpisode.thumbnailUrl"
            />
            <div class="p-4">
                <h1>
                    S{{ selectedEpisode.seasonNo }} E{{
                        selectedEpisode.episodeNo
                    }}: {{ selectedEpisode.title }}
                </h1>
                <h2>Aired {{ reformatDate(selectedEpisode.airedOn) }}</h2>
                <p class="raw-text">{{ selectedEpisode.synopsis }}</p>
                <button v-on:click="selectedEpisode = null">Close</button>
            </div>
        </modal>
    </div>
</template>

<script>
import FluidImage from '../components/FluidImage.vue';
import Modal from '../components/Modal.vue';
import Icon from '../components/Icon.vue';
import Api from '../services/api';
export default {
    name: 'Anime',
    components: {
        Icon,
        Modal,
        FluidImage,
    },
    data() {
        return {
            loading: true,
            anime: null,
            selectedCharacter: null,
            selectedEpisode: null,
        };
    },
    computed: {
        englishTitle() {
            return this.getTitle(['en', 'en_us', 'en_jp']);
        },
        japaneseTitle() {
            return this.getTitle(['ja_jp', 'ja', 'en_jp']);
        },
        romajiTitle() {
            return this.getTitle(['en_jp']);
        },
        totalDuration() {
            let totalLength = this.anime.totalLength;
            return totalLength < 60
                ? totalLength + ' minutes'
                : (totalLength / 60).toFixed(1) + ' hours';
        },
        airingStartedOn() {
            return this.reformatDate(this.anime.airingStartedOn);
        },
        airingEndedOn() {
            return this.reformatDate(this.anime.airingEndedOn);
        },
        airingDuration() {
            let start = this.airingStartedOn;
            let end = this.airingEndedOn;
            return start == end ? start : `${start} - ${end}`;
        },
        status() {
            let status = this.anime.status;
            switch (status) {
                case 'airing':
                    return 'Airing';
                case 'finished':
                    return 'Finished';
                case 'toBeAnnounced':
                    return 'To be announced';
                case 'unreleased':
                    return 'Unreleased';
                case 'upcoming':
                    return 'Upcoming';
            }
            return 'Unknown';
        },
        type() {
            let type = this.anime.type;
            switch (type) {
                case 'ona':
                    return 'Original net animation';
                case 'ova':
                    return 'Original video animation';
                case 'tv':
                    return 'TV Show';
                case 'movie':
                    return 'Movie';
                case 'music':
                    return 'Music';
                case 'special':
                    return 'Special';
            }
            return 'Unknown';
        },
    },
    async mounted() {
        let animeId = this.$route.params.animeId;
        this.anime = await Api.Anime.get(animeId);
        this.loading = false;
        console.dir(this.anime);

        this.$nextTick(this.bindClickHandlers);
    },
    methods: {
        getTitle(preferredLanguages) {
            let titles = this.anime.titles;

            function getSingleTitle(langugae) {
                for (let title of titles) {
                    if (title.language == langugae) return title.value;
                }
                return null;
            }

            for (let preferredLanguage of preferredLanguages) {
                let title = getSingleTitle(preferredLanguage);
                if (title != null) return title;
            }

            let firstOne = Object.keys(titles)[0];
            return titles[firstOne];
        },
        reformatDate(date) {
            if (date.includes('T')) date = date.substr(0, date.indexOf('T'));
            let parsedDate = new Date(Date.parse(date));
            return parsedDate.toShortFormat();
        },
        bindClickHandlers() {
            let episodes = document.getElementsByClassName('anime-episode');
            for (let episode of episodes)
                episode.onclick = () => {
                    this.showEpisodeDetails(episode.getAttribute('data-no'));
                };

            let characters = document.getElementsByClassName('anime-character');
            for (let character of characters) {
                character.onclick = () => {
                    this.showCharacterDetails(
                        character.getAttribute('data-name')
                    );
                };
            }
        },
        showEpisodeDetails(no) {
            for (let episode of this.anime.episodes) {
                if (episode.episodeNo == no) {
                    this.selectedEpisode = episode;
                    break;
                }
            }
        },
        showCharacterDetails(name) {
            for (let character of this.anime.characters) {
                if (character.name == name) {
                    this.selectedCharacter = character;
                    break;
                }
            }
        },
    },
};
</script>

<style scoped>
@import '../style/button.css';

h1 {
    color: var(--primary-color);
    font-weight: 700;
    font-size: 2rem;
    margin-bottom: 1rem;
}
h2 {
    font-weight: 500;
    font-size: 1.1rem;
}
h3 {
    margin-top: 1rem;
    margin-bottom: 0.8rem;
    font-weight: 700;
    font-size: 1.1rem;
}
h4 {
    font-size: 1rem;
    font-weight: 700;
    margin-top: 1rem;
    margin-bottom: 0.25rem;
}

.anime-overview {
    width: 100%;
    box-shadow: 0px -3px 4px 0px rgba(0, 0, 0, 0.1);
}
.anime-banner {
    background-position: center;
    background-color: #eeeeee;
    background-size: cover;
    width: 100%;
    height: 350px;
}
.anime-header {
    display: flex;
    margin: auto;
}
.anime-meta {
    height: 140px;
    flex-grow: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-left: 3rem;
}
.anime-poster {
    background-position: center;
    background-color: #eeeeee;
    width: 255px;
    height: 360px;
    margin-top: -220px;
    border-radius: 5px;
    background-size: cover;
    box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.3);
}

.anime-synopsis {
    margin-top: 2rem;
    padding-bottom: 2rem;
}
.anime-details {
    padding-top: 1rem;
    background-color: #f9f9f9;
}

.details-pane {
    box-shadow: 0px 0px 3px 0px rgba(0, 0, 0, 0.15);
    background: white;
    margin-bottom: 2rem;
    border-radius: 5px;
    margin-top: 1rem;
    padding: 1.75rem 1.5rem;
    width: 320px;
}

.col,
.col-auto {
    padding-left: 0;
    padding-right: 0;
}

.anime-trailer {
    width: 100%;
    height: 110px;
    border-radius: 5px;
    background-position: center;
    background-size: cover;
    margin-bottom: 2rem;
}
.anime-trailer-link:hover {
    transition-duration: 0.3s;
    opacity: 0.95;
}
.anime-trailer-link {
    display: block;
    width: 100%;
    height: 100%;
    border-radius: 5px;
    backdrop-filter: blur(40px);
    text-align: center;
    line-height: 110px;
    color: white;
    text-decoration: none;
    font-weight: 700;
    font-size: 25px;
    transition-duration: 0.3s;
}

.anime-block {
    position: relative;
    border-radius: 5px;
    display: inline-block;
    background-position: center;
    background-size: cover;
    margin: 10px;
    transition-duration: 0.3s;
    user-select: none;
}
.anime-block:hover {
    cursor: pointer;
    opacity: 0.9;
    transition-duration: 0.3s;
}
.anime-block-title {
    position: absolute;
    color: white;
    backdrop-filter: brightness(65%) blur(5px);
    border-bottom-left-radius: 5px;
    border-bottom-right-radius: 5px;
    padding: 6px 8px;
    left: 0;
    right: 0;
    bottom: 0;
}
.anime-episode {
    width: 325px;
    height: 180px;
}
.anime-character {
    width: 150px;
    height: 230px;
}

.episode-detail-thumbnail {
    width: 100%;
    height: 15rem;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
}
</style>
