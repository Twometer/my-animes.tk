<template>
    <section
        class="anime-banner"
        :style="{
            'background-image': `url('https://media.kitsu.io/anime/cover_images/13600/original.jpg?1597702991')`,
        }"
    ></section>
    <section class="anime-overview">
        <section class="anime-header w-80">
            <div
                class="anime-poster"
                :style="{
                    'background-image': `url('https://media.kitsu.io/anime/poster_images/13600/small.jpg?1597697521')`,
                }"
            />
            <div class="anime-meta w-80">
                <section>
                    <h1>DARLING in the FRANXX</h1>
                    <h2>ダーリン・イン・ザ・フランキス</h2>
                </section>

                <button>Add to list</button>
            </div>
        </section>
        <section class="anime-synopsis w-80">
            The distant future: Humanity established the mobile fort city,
            Plantation, upon the ruined wasteland. Within the city were pilot
            quarters, Mistilteinn, otherwise known as the “Birdcage.” That is
            where the children live... Their only mission in life was the fight.
            Their enemies are the mysterious giant organisms known as Kyoryu.
            The children operate robots known as FRANXX in order to face these
            still unseen enemies. Among them was a boy who was once called a
            child prodigy: Code number 016, Hiro. One day, a mysterious girl
            called Zero Two appears in front of Hiro. “I’ve found you, my
            Darling.”
            <br /><br />
            (Source: Crunchyroll)
        </section>
    </section>
    <section class="anime-details">
        <div class="row w-80">
            <div class="col">
                <h3>Episodes</h3>
                <div
                    class="anime-block anime-episode"
                    :style="{
                        'background-image': `url('https://media.kitsu.io/episodes/thumbnails/234664/original.jpg?1597700799')`,
                    }"
                >
                    <div class="anime-block-title">Episode title here</div>
                </div>

                <h3 class="mt-5">Characters</h3>
                <div
                    class="anime-block anime-character"
                    :style="{
                        'background-image': `url('https://s4.anilist.co/file/anilistcdn/character/large/b124381-pkTKi6HHNuVR.png')`,
                    }"
                >
                    <div class="anime-block-title">Zero Two</div>
                </div>
            </div>
            <div class="col-auto">
                <div class="details-pane">
                    <h4 class="mt-0">Romaji Title</h4>
                    Darling in the FranXX

                    <h4>Status</h4>
                    Finished

                    <h4>Type</h4>
                    TV Show

                    <h4>Studios</h4>
                    A1, Trigger, CLOVERWORKS

                    <h4>Airing</h4>
                    13 Jan 2018 - 07 Jul 2018

                    <h4>Genres</h4>
                    Sci-Fi, Mecha, Drama, Romance, Action

                    <h4>Episodes</h4>
                    24

                    <h4>Duration</h4>
                    9.6 hours (24 min per episode)

                    <h4>Age rating</h4>
                    Teens 13 or older

                    <h4>NSFW</h4>
                    No
                </div>
                <div
                    class="anime-trailer"
                    :style="{
                        'background-image': `url('https://i.ytimg.com/vi/Q4jDgDSV6Kk/hqdefault.jpg')`,
                    }"
                >
                    <a href="#" class="anime-trailer-link">
                        <icon name="youtube" size="30" />&nbsp;&nbsp;Watch
                        trailer
                    </a>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import Icon from '../components/Icon.vue';
export default {
    name: 'Anime',
    components: {
        Icon,
    },
};
</script>

<style scoped>
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
}

.anime-block {
    border-radius: 5px;
    display: flex;
    flex-flow: row;
    align-items: flex-end;
    background-position: center;
    background-size: cover;
}
.anime-block-title {
    color: white;
    backdrop-filter: brightness(65%) blur(5px);
    border-bottom-left-radius: 5px;
    border-bottom-right-radius: 5px;
    padding: 6px 8px;
    width: 100%;
}
.anime-episode {
    width: 325px;
    height: 180px;
    transition-duration: .3s;
}
.anime-episode:hover {
    cursor: pointer;
    opacity: .9;
    transition-duration: .3s;
}
.anime-character {
    width: 150px;
    height: 230px;
}

button {
    display: block;
    border: 1px solid var(--primary-color);
    background: white;
    padding: 3px 18px;
    border-radius: 5px;
    box-shadow: 0px 1.5px 6px 0px rgba(0, 0, 0, 0.3);
    font-family: Exo;
    font-size: 18px;
    transition-duration: .3s;
    color: var(--text-color);
}
button:hover {
    background: #efefef;
    transition-duration: .3s;
}
</style>