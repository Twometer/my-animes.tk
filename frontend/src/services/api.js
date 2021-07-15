import axios from 'axios';

export default {
    Anime: {
        async get(id) {
            return (await axios.get(`anime/${id}`)).data;
        }
    },
    Session: {
        async login(username, password) {
            return await axios.post('session/login', { username, password });
        },
        async logout() {
            return await axios.post('session/logout');
        }
    },
    Search: {
        async search(query) {
            let encodedQuery = encodeURIComponent(query);
            return (await axios.get(`search?q=${encodedQuery}`)).data;
        }
    },
    User: {
        async new(username, password) {
            return await axios.post('user/new', { username, password });
        },
        async delete(username, password) {
            return await axios.delete(`user/${username}`, { password })
        },
        async getProfile(username) {
            return await axios.get(`user/${username}/profile`)
        },
        async updateProfile(username, profile) {
            return await axios.put(`user/${username}/profile`, profile)
        },
        async updatePassword(username, oldPassword, newPassword) {
            return await axios.put(`user/${username}/password`, { oldPassword, newPassword })
        },
        async getWatchlist(username) {
            return await axios.get(`user/${username}/list`)
        },
        async addWatchlistItem(username, item) {
            return await axios.post(`user/${username}/list/new`, item)
        },
        async updateWatchlistItem(username, itemId, item) {
            return await axios.put(`user/${username}/list/${itemId}`, item)
        },
        async deleteWatchlistItem(username, itemId) {
            return await axios.delete(`user/${username}/list/${itemId}`)
        }
    }
}