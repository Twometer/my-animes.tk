import * as db from '../database'
import {Webapp, error} from "@twometer/webcore";

export default function (webapp: Webapp) {
    const route = webapp.route('/api/user')

    // Create a new user
    route.post('/new', async (req, resp) => {
        if (req.session.loggedIn)
            return error(resp, 400, 'Already logged in')

    })

    // Delete a user account [Requires auth]
    route.delete('/:username', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

    })

    // Get the profile for the given username
    route.get('/:username/profile', async (req, resp) => {
        let user = await db.User.findOne({username: req.params.username})
        if (user == null)
            return error(resp, 404)

        return resp.json(user.profile);
    })

    // Update the profile for the given username [Requires auth]
    route.put('/:username/profile', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

    });

    // Change the password for the given username [Requires auth]
    route.put('/:username/password', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

    });

    // Get the watchlist for the given username
    route.get('/:username/list', async (req, resp) => {

    });

    // Add a new anime to the watchlist [Requires auth]
    route.post('/:username/list/new', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

    });

    // Update the data for one item on the watchlist [Requires auth]
    route.put('/:username/list/:itemId', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

    });

    // Remove an item from the watchlist [Requires auth]
    route.delete('/:username/list/:itemId', async (req, resp) => {
        if (!req.session.loggedIn || req.session.username != req.params.username)
            return error(resp, 401)

    });
}