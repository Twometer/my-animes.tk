import axios from 'axios';

export default {

    Anime: {
        async get(id) {
            return (await axios.get(`anime/${id}`)).data;
        }
    }

}