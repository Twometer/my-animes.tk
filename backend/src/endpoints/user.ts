import * as db from '../database'
import {Webapp, error, Request} from "@twometer/webcore";
import {hash} from "../hash";
import * as uuid from 'uuid';
import * as kitsu from "../kitsu"

const NewUserRequest = new Request({
    username: String,
    password: String
})

const UpdateProfileRequest = new Request({
    aboutMe: String,
    location: String,
    avatarUrl: String,
    favoriteAnimeId: String,
    favoriteCharacter: String
})

const ChangePasswordRequest = new Request({
    oldPassword: String,
    newPassword: String
})

const WatchlistEntryRequest = new Request({
    animeId: String,
    state: String,
    startedOn: String,
    finishedOn: String
})

export default function (webapp: Webapp) {
    const route = webapp.route('/api/user')

    // Create a new user
    route.post('/new', async (req, resp) => {
        if (req.session.loggedIn)
            return error(resp, 400, 'Already logged in')

        let body = NewUserRequest.parse(req.body, resp);
        if (body == null) return;

        if (req.body.password.trim().length < 8)
            return error(resp, 400, 'Password must be 8 characters long')

        let existingUsers = await db.User.countDocuments({username: req.body.username});
        if (existingUsers != 0)
            return error(resp, 400, 'Account already exists')

        let user = new db.User({
            _id: uuid.v4(),
            username: req.body.username,
            password: await hash(req.body.password, req.body.username),
            profile: {
                aboutMe: "",
                avatarUrl: "",
                location: "",
                favoriteAnimeId: "",
                favoriteCharacter: ""
            }
        });
        await user.save();

        return resp.sendStatus(201);
    })

    // Delete a user account [Requires strong auth]
    route.delete('/:username', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

        return error(resp, 501);
    })

    // Get the profile for the given username
    route.get('/:username/profile', async (req, resp) => {
        let user = await db.User.findOne({username: req.params.username})
        if (user == null)
            return error(resp, 404)

        if (user.profile.avatarUrl == "") {
            user.profile.avatarUrl = `https://eu.ui-avatars.com/api/?name=${user.username}&background=876FFF&color=ffffff&size=194`
        }

        return resp.json(user.profile);
    })

    // Update the profile for the given username [Requires auth]
    route.put('/:username/profile', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

        let body = UpdateProfileRequest.parse(req.body, resp);
        if (body == null) return;

        let user = await db.User.findOne({username: req.params.username});
        if (user == null) return error(resp, 404);

        if (body.favoriteAnimeId != user.profile.favoriteAnimeId && body.favoriteAnimeId != '') {
            let anime = await kitsu.load(body.favoriteAnimeId);
            if (anime == null)
                return error(resp, 400, 'Favorite anime does not exist');
        }

        Object.assign(user.profile, body);
        await user.save();

        return resp.sendStatus(204);
    });

    // Change the password for the given username [Requires strong auth]
    route.put('/:username/password', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

        let body = ChangePasswordRequest.parse(req.body, resp);
        if (body == null) return;

        if (req.body.newPassword.trim().length < 8)
            return error(resp, 400, 'New password must be 8 characters long')

        let user = await db.User.findOne({username: req.params.username});
        if (user == null) return error(resp, 404);

        let hashedInput = await hash(body.oldPassword, body.username);
        if (user.password != hashedInput)
            return error(resp, 401, 'Invalid credentials');

        user.password = await hash(body.newPassword, body.username);
        return resp.sendStatus(204);
    });

    // Get the watchlist for the given username
    route.get('/:username/list', async (req, resp) => {
        let user = await db.User.findOne({username: req.params.username});
        if (user == null) return error(resp, 404);

        let watchlist = await db.WatchlistEntry.find({userId: user._id});
        return resp.json(watchlist);
    });

    // Add a new anime to the watchlist [Requires auth]
    route.post('/:username/list/new', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

        let body = WatchlistEntryRequest.parse(req.body, resp);
        if (body == null) return;

        let existingEntries = await db.WatchlistEntry.countDocuments({
            userId: req.session.userId,
            animeId: body.animeId
        });
        if (existingEntries != 0)
            return error(resp, 400, 'Entry already exists')

        let entry = new db.WatchlistEntry({
            _id: uuid.v4(),
            userId: req.session.userId
        });
        Object.assign(entry, body);
        await entry.save();

        return resp.sendStatus(201);
    });

    // Update the data for one item on the watchlist [Requires auth]
    route.put('/:username/list/:itemId', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

        let body = WatchlistEntryRequest.parse(req.body, resp);
        if (body == null) return;

        let entry = await db.WatchlistEntry.findOne({_id: req.params.itemId});
        if (entry == null) return error(resp, 404);

        if (entry.userId != req.session.userId)
            return error(resp, 401);

        Object.assign(entry, body);
        await entry.save();

        return resp.sendStatus(204);
    });

    // Remove an item from the watchlist [Requires auth]
    route.delete('/:username/list/:itemId', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

        let entry = await db.WatchlistEntry.findOne({_id: req.params.itemId});
        if (entry == null) return error(resp, 404);

        if (entry.userId != req.session.userId)
            return error(resp, 401);

        await entry.delete();
        return resp.sendStatus(204);
    });
}